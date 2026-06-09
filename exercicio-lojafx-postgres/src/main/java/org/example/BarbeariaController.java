package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.barbearia.BarbeariaDAO;
import org.example.dao.barbearia.EquipamentoDAO;
import org.example.enums.barbearia.EquipamentoEstado;
import org.example.model.barbearia.BarbeariaModel;
import org.example.model.barbearia.EquipamentoModel;

import java.io.IOException;

public class BarbeariaController {

    @FXML private TextField txtFldMarcaEquipamento;
    @FXML private TextField txtFldModeloEquipamento;
    @FXML private CheckBox checkManutencao;
    @FXML private TableView<EquipamentoModel> tabelaEquipamentos;
    @FXML private TableColumn<EquipamentoModel, String> colMarca;
    @FXML private TableColumn<EquipamentoModel, String> colModelo;
    @FXML private TableColumn<EquipamentoModel, EquipamentoEstado> colEstado;
    @FXML private TableColumn<EquipamentoModel, Boolean> colManutencao;
    @FXML private Label lblStatusBarbearia;
    @FXML private Button btnLigarDesligar;

    private final BarbeariaDAO barbeariaDAO = new BarbeariaDAO();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();

    private ObservableList<EquipamentoModel> equipamentos = FXCollections.observableArrayList();
    private BarbeariaModel barbearia;

    @FXML
    public void initialize() {
        configurarTabela();
        tabelaEquipamentos.setItems(equipamentos);
        tabelaEquipamentos.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> atualizarBotaoLigarDesligar(novo));

        barbearia = barbeariaDAO.buscarOuCriar();
        lblStatusBarbearia.setText("Status: " + (barbearia.isEstaAberto() ? "aberta" : "fechada"));

        carregarEquipamentosDoBanco();
    }

    private void carregarEquipamentosDoBanco() {
        equipamentos.setAll(equipamentoDAO.listarPorBarbearia(barbearia.getId()));
        // sincroniza a lista do modelo em memória também
        barbearia.getEquipamentos().clear();
        barbearia.getEquipamentos().addAll(equipamentos);
    }

    private void configurarTabela() {
        colMarca.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getMarca()));
        colModelo.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getModelo()));
        colEstado.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getEquipamentoEstado()));
        colManutencao.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleBooleanProperty(cd.getValue().isRecebeuManuntencao()).asObject());
    }

    private void atualizarBotaoLigarDesligar(EquipamentoModel selecionado) {
        if (selecionado == null) { btnLigarDesligar.setText("Ligar"); return; }
        btnLigarDesligar.setText(
                selecionado.getEquipamentoEstado() == EquipamentoEstado.LIGADO ? "Desligar" : "Ligar");
    }

    @FXML
    public void registrarEquipamento() {
        String marca = txtFldMarcaEquipamento.getText().trim();
        String modelo = txtFldModeloEquipamento.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        EquipamentoModel novoEquipamento = new EquipamentoModel(true, modelo, marca, EquipamentoEstado.DESLIGADO);
        novoEquipamento.setBarbeariaId(barbearia.getId());
        equipamentoDAO.salvar(novoEquipamento);

        equipamentos.add(novoEquipamento);
        barbearia.registrarEquipamento(novoEquipamento);
        limparCampos();
    }

    @FXML
    public void usarEquipamento() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um equipamento para usar."); return; }

        barbearia.usarEquipamento(selecionado);
        equipamentoDAO.atualizar(selecionado);
        tabelaEquipamentos.refresh();
    }

    @FXML
    public void fazerManutencao() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um equipamento para fazer manutenção."); return; }

        selecionado.receberManutencao();
        equipamentoDAO.atualizar(selecionado);
        tabelaEquipamentos.refresh();
    }

    @FXML
    public void ligarDesligar() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um equipamento."); return; }

        if (selecionado.getEquipamentoEstado() == EquipamentoEstado.LIGADO) selecionado.desligar();
        else selecionado.ligar();

        equipamentoDAO.atualizar(selecionado);
        tabelaEquipamentos.refresh();
        atualizarBotaoLigarDesligar(selecionado);
    }

    @FXML
    public void abrirBarbearia() {
        barbearia.abrir();
        barbeariaDAO.atualizar(barbearia);
        lblStatusBarbearia.setText("Status: aberta");
    }

    @FXML
    public void fecharBarbearia() {
        barbearia.fechar();
        barbeariaDAO.atualizar(barbearia);
        lblStatusBarbearia.setText("Status: fechada");
    }

    @FXML
    public void checarManutencao() {
        barbearia.checarManutencaoEquipamentos();
        barbeariaDAO.atualizar(barbearia);
        mostrarAlerta("Manutenção", barbearia.possuiEquipamentosSemManutencao()
                ? "Há equipamentos que precisam de manutenção!"
                : "Todos os equipamentos estão em dia.");
    }

    private void limparCampos() {
        txtFldMarcaEquipamento.clear();
        txtFldModeloEquipamento.clear();
        checkManutencao.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void voltarMenu() throws IOException {
        App.setRoot("menu");
    }
}
