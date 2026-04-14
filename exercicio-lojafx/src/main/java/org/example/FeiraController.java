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

        double salario;
        try {
            salario = Double.parseDouble(salarioTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Salário inválido.");
            return;
        }

        // horas começam em 0
        FuncionarioModel novoFuncionario = new FuncionarioModel(nome, salario, 0);
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

        if (selecionado.getHorasTrabalhadasSemana() == 0){
            mostrarAlerta("Atenção", "Funcionário não tem horas trabalhadas a receber");
            return;
        }
        mostrarAlerta("Atenção", selecionado.getNome() + " recebeu R$" + selecionado.receberPagamento());
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