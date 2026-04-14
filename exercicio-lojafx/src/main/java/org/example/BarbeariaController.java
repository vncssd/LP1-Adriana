package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private ObservableList<EquipamentoModel> equipamentos = FXCollections.observableArrayList();
    private BarbeariaModel barbearia = new BarbeariaModel();

    @FXML
    public void initialize() {
        configurarTabela();
        tabelaEquipamentos.setItems(equipamentos);
        tabelaEquipamentos.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> atualizarBotaoLigarDesligar(novo)
        );
    }

    private void configurarTabela() {
        colMarca.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca())
        );
        colModelo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModelo())
        );
        colEstado.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEquipamentoEstado())
        );
        colManutencao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().isRecebeuManuntencao()).asObject()
        );
    }

    private void atualizarBotaoLigarDesligar(EquipamentoModel selecionado) {
        if (selecionado == null) {
            btnLigarDesligar.setText("Ligar");
            return;
        }
        btnLigarDesligar.setText(
                selecionado.getEquipamentoEstado() == EquipamentoEstado.LIGADO ? "Desligar" : "Ligar"
        );
    }

    @FXML
    public void registrarEquipamento() {
        String marca = txtFldMarcaEquipamento.getText().trim();
        String modelo = txtFldModeloEquipamento.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        boolean manutencao = checkManutencao.isSelected();

        // todos os equipamentos começam DESLIGADOS por padrão
        EquipamentoModel novoEquipamento = new EquipamentoModel(manutencao, modelo, marca, EquipamentoEstado.DESLIGADO);
        barbearia.registrarEquipamento(novoEquipamento);
        equipamentos.add(novoEquipamento);

        limparCampos();
    }

    @FXML
    public void usarEquipamento() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um equipamento para usar.");
            return;
        }

        barbearia.usarEquipamento(selecionado);
        tabelaEquipamentos.refresh();
    }

    @FXML
    public void fazerManutencao() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um equipamento para fazer manutenção.");
            return;
        }

        selecionado.receberManutencao();
        tabelaEquipamentos.refresh();
    }

    @FXML
    public void ligarDesligar() {
        EquipamentoModel selecionado = tabelaEquipamentos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um equipamento.");
            return;
        }

        if (selecionado.getEquipamentoEstado() == EquipamentoEstado.LIGADO) {
            selecionado.desligar();
        } else {
            selecionado.ligar();
        }

        tabelaEquipamentos.refresh();
        atualizarBotaoLigarDesligar(selecionado);
    }

    @FXML
    public void abrirBarbearia() {
        barbearia.abrir();
        lblStatusBarbearia.setText("Status: aberta");
    }

    @FXML
    public void fecharBarbearia() {
        barbearia.fechar();
        lblStatusBarbearia.setText("Status: fechada");
    }

    @FXML
    public void checarManutencao() {
        barbearia.checarManutencaoEquipamentos();
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