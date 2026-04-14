package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.feira.BarracaModel;
import org.example.model.feira.FuncionarioModel;

import java.io.IOException;

public class FeiraController {

    @FXML
    private TextField txtFldNomeFuncionario;

    @FXML
    private TextField txtFldSalarioFuncionario;

    @FXML
    private Spinner<Integer> spinnerHoras;

    @FXML
    private TableView<FuncionarioModel> tabelaFuncionarios;

    @FXML
    private TableColumn<FuncionarioModel, String> colNome;

    @FXML
    private TableColumn<FuncionarioModel, Double> colSalario;

    @FXML
    private TableColumn<FuncionarioModel, Integer> colHoras;

    @FXML
    private Label lblStatusBarraca;

    private ObservableList<FuncionarioModel> funcionarios = FXCollections.observableArrayList();
    private BarracaModel barraca = new BarracaModel();

    @FXML
    public void initialize() {
        configurarTabela();
        tabelaFuncionarios.setItems(funcionarios);

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1);
        spinnerHoras.setValueFactory(valueFactory);
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome())
        );
        colSalario.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSalarioPorHora())
        );
        colHoras.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHorasTrabalhadasSemana())
        );
    }

    @FXML
    public void contratarFuncionario() {
        String nome = txtFldNomeFuncionario.getText().trim();
        String salarioTexto = txtFldSalarioFuncionario.getText().trim();

        if (nome.isEmpty() || salarioTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        double salario = Double.parseDouble(salarioTexto);

        int horas = spinnerHoras.getValue();

        FuncionarioModel novoFuncionario = new FuncionarioModel(nome, salario, horas);
        barraca.contratarFuncionario(novoFuncionario);
        funcionarios.add(novoFuncionario);

        limparCampos();
    }

    @FXML
    public void demitirFuncionario() {
        FuncionarioModel selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um funcionário para demitir.");
            return;
        }

        barraca.demitirFuncionario(selecionado.getNome());
        funcionarios.remove(selecionado);
        mostrarAlerta("Atenção", selecionado.getNome()+ " foi demitido(a)");
    }

    @FXML
    public void trabalhar() {
        FuncionarioModel selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um funcionário para trabalhar.");
            return;
        }

        selecionado.trabalhar(8);
        tabelaFuncionarios.refresh();
    }

    @FXML
    public void pagarSalario() {
        FuncionarioModel selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um funcionário para pagar.");
            return;
        }

        selecionado.receberPagamento();
        selecionado.setHorasTrabalhadasSemana(0);
        tabelaFuncionarios.refresh();
    }

    @FXML
    public void abrirBarraca() {
        String resultado = barraca.abrirBarraca();
        lblStatusBarraca.setText("Status: " + resultado);
    }

    private void limparCampos() {
        txtFldNomeFuncionario.clear();
        txtFldSalarioFuncionario.clear();
        spinnerHoras.getValueFactory().setValue(1);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void voltarMenu() throws IOException {
        App.setRoot("menu");
    }
}