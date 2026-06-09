package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.farmacia.FarmaciaDAO;
import org.example.dao.farmacia.FarmaceuticoDAO;
import org.example.dao.farmacia.MedicamentoDAO;
import org.example.enums.farmacia.TipoMedicamento;
import org.example.model.farmacia.FarmaciaModel;
import org.example.model.farmacia.FarmaceuticoModel;
import org.example.model.farmacia.MedicamentoModel;

import java.io.IOException;
import java.util.List;

public class FarmaciaController {

    // --- medicamentos
    @FXML private TextField txtNomeMedicamento;
    @FXML private TextField txtPrecoMedicamento;
    @FXML private TextField txtQtdMedicamento;
    @FXML private ComboBox<TipoMedicamento> comboTipo;
    @FXML private TableView<MedicamentoModel> tabelaMedicamentos;
    @FXML private TableColumn<MedicamentoModel, String> colNomeMed;
    @FXML private TableColumn<MedicamentoModel, Double> colPrecoMed;
    @FXML private TableColumn<MedicamentoModel, Integer> colEstoqueMed;
    @FXML private TableColumn<MedicamentoModel, Integer> colVendidoMed;
    @FXML private TableColumn<MedicamentoModel, TipoMedicamento> colTipoMed;

    // --- farmacêuticos
    @FXML private TextField txtNomeFarmaceutico;
    @FXML private TextField txtCrfFarmaceutico;
    @FXML private TableView<FarmaceuticoModel> tabelaFarmaceuticos;
    @FXML private TableColumn<FarmaceuticoModel, String> colNomeFarm;
    @FXML private TableColumn<FarmaceuticoModel, String> colCrfFarm;
    @FXML private TableColumn<FarmaceuticoModel, Boolean> colServicoFarm;

    // --- status
    @FXML private Label lblStatusFarmacia;

    private final FarmaciaDAO farmaciaDAO = new FarmaciaDAO();
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    private final FarmaceuticoDAO farmaceuticoDAO = new FarmaceuticoDAO();

    private ObservableList<MedicamentoModel> medicamentos = FXCollections.observableArrayList();
    private ObservableList<FarmaceuticoModel> farmaceuticos = FXCollections.observableArrayList();
    private FarmaciaModel farmacia;

    @FXML
    public void initialize() {
        configurarTabelaMedicamentos();
        configurarTabelaFarmaceuticos();
        tabelaMedicamentos.setItems(medicamentos);
        tabelaFarmaceuticos.setItems(farmaceuticos);
        comboTipo.setItems(FXCollections.observableArrayList(TipoMedicamento.values()));
        comboTipo.setValue(TipoMedicamento.LIVRE);

        farmacia = farmaciaDAO.buscarOuCriar();
        atualizarStatusLabel();
        carregarDados();
    }

    private void carregarDados() {
        medicamentos.setAll(medicamentoDAO.listarPorFarmacia(farmacia.getId()));
        farmacia.getMedicamentos().clear();
        farmacia.getMedicamentos().addAll(medicamentos);
        farmaceuticos.setAll(farmaceuticoDAO.listarPorFarmacia(farmacia.getId()));
    }

    private void configurarTabelaMedicamentos() {
        colNomeMed.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getNome()));
        colPrecoMed.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getPreco()));
        colEstoqueMed.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getQuantidadeEmEstoque()));
        colVendidoMed.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getQuantidadeVendida()));
        colTipoMed.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getTipo()));
    }

    private void configurarTabelaFarmaceuticos() {
        colNomeFarm.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getNome()));
        colCrfFarm.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getCrf()));
        colServicoFarm.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleBooleanProperty(cd.getValue().isEstaDeServico()).asObject());
    }

    // ---- ações medicamento ----

    @FXML
    public void cadastrarMedicamento() {
        String nome = txtNomeMedicamento.getText().trim();
        String precoTxt = txtPrecoMedicamento.getText().trim();
        String qtdTxt = txtQtdMedicamento.getText().trim();
        TipoMedicamento tipo = comboTipo.getValue();

        if (nome.isEmpty() || precoTxt.isEmpty() || qtdTxt.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }
        try {
            double preco = Double.parseDouble(precoTxt.replace(",", "."));
            int qtd = Integer.parseInt(qtdTxt);

            MedicamentoModel existente = medicamentoDAO.buscarPorNome(nome, farmacia.getId());
            if (existente != null) {
                existente.reabastecer(qtd);
                medicamentoDAO.atualizar(existente);
            } else {
                MedicamentoModel novo = new MedicamentoModel(nome, preco, qtd, tipo);
                novo.setFarmaciaId(farmacia.getId());
                medicamentoDAO.salvar(novo);
            }
            carregarDados();
            limparCamposMedicamento();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Preço ou quantidade inválidos.");
        }
    }

    @FXML
    public void venderMedicamento() {
        MedicamentoModel selecionado = tabelaMedicamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um medicamento."); return; }
        if (!selecionado.vender()) { mostrarAlerta("Atenção", "Medicamento sem estoque."); return; }
        medicamentoDAO.atualizar(selecionado);
        tabelaMedicamentos.refresh();
    }

    @FXML
    public void removerMedicamento() {
        MedicamentoModel selecionado = tabelaMedicamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um medicamento."); return; }
        medicamentoDAO.deletar(selecionado.getId());
        medicamentos.remove(selecionado);
    }

    @FXML
    public void verificarEstoqueCritico() {
        List<MedicamentoModel> criticos = farmacia.verificarEstoqueCritico(5);
        if (criticos.isEmpty()) {
            mostrarAlerta("Estoque", "Todos os medicamentos estão com estoque adequado.");
        } else {
            StringBuilder sb = new StringBuilder("Estoque crítico (≤ 5 un):\n");
            criticos.forEach(m -> sb.append("• ").append(m.getNome())
                    .append(": ").append(m.getQuantidadeEmEstoque()).append(" un\n"));
            mostrarAlerta("Estoque Crítico", sb.toString());
        }
    }

    // ---- ações farmacêutico ----

    @FXML
    public void cadastrarFarmaceutico() {
        String nome = txtNomeFarmaceutico.getText().trim();
        String crf = txtCrfFarmaceutico.getText().trim();
        if (nome.isEmpty() || crf.isEmpty()) { mostrarAlerta("Erro", "Preencha nome e CRF."); return; }

        FarmaceuticoModel f = new FarmaceuticoModel(nome, crf);
        f.setFarmaciaId(farmacia.getId());
        farmaceuticoDAO.salvar(f);
        farmaceuticos.add(f);
        txtNomeFarmaceutico.clear();
        txtCrfFarmaceutico.clear();
    }

    @FXML
    public void alternarServico() {
        FarmaceuticoModel selecionado = tabelaFarmaceuticos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um farmacêutico."); return; }
        String msg = selecionado.isEstaDeServico() ? selecionado.sairDeServico() : selecionado.entrarDeServico();
        farmaceuticoDAO.atualizar(selecionado);
        tabelaFarmaceuticos.refresh();
        mostrarAlerta("Serviço", msg);
    }

    @FXML
    public void removerFarmaceutico() {
        FarmaceuticoModel selecionado = tabelaFarmaceuticos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um farmacêutico."); return; }
        farmaceuticoDAO.deletar(selecionado.getId());
        farmaceuticos.remove(selecionado);
    }

    // ---- ações farmácia ----

    @FXML
    public void abrirFarmacia() {
        String resultado = farmacia.abrir();
        farmaciaDAO.atualizar(farmacia);
        atualizarStatusLabel();
        mostrarAlerta("Farmácia", resultado);
    }

    @FXML
    public void fecharFarmacia() {
        String resultado = farmacia.fechar();
        farmaciaDAO.atualizar(farmacia);
        atualizarStatusLabel();
        mostrarAlerta("Farmácia", resultado);
    }

    private void atualizarStatusLabel() {
        lblStatusFarmacia.setText("Status: " + (farmacia.isEstaAberta() ? "aberta" : "fechada"));
    }

    private void limparCamposMedicamento() {
        txtNomeMedicamento.clear();
        txtPrecoMedicamento.clear();
        txtQtdMedicamento.clear();
        comboTipo.setValue(TipoMedicamento.LIVRE);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void voltarMenu() throws IOException {
        App.setRoot("menu");
    }
}
