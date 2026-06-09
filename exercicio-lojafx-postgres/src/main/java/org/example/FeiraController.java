package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.feira.BarracaDAO;
import org.example.dao.feira.FuncionarioDAO;
import org.example.dao.feira.ProdutoFeiraDAO;
import org.example.model.feira.BarracaModel;
import org.example.model.feira.FuncionarioModel;
import org.example.model.feira.ProdutoFeiraModel;

import java.io.IOException;

public class FeiraController {

    @FXML private TextField txtFldNomeFuncionario;
    @FXML private TextField txtFldSalarioFuncionario;
    @FXML private TableView<FuncionarioModel> tabelaFuncionarios;
    @FXML private TableColumn<FuncionarioModel, String> colNome;
    @FXML private TableColumn<FuncionarioModel, Double> colSalario;
    @FXML private TableColumn<FuncionarioModel, Integer> colHoras;
    @FXML private Label lblStatusBarraca;

    // campos para produtos da feira
    @FXML private TextField txtFldNomeProdutoFeira;
    @FXML private TextField txtFldPrecoProdutoFeira;
    @FXML private TextField txtFldQtdProdutoFeira;
    @FXML private TableView<ProdutoFeiraModel> tabelaProdutosFeira;
    @FXML private TableColumn<ProdutoFeiraModel, String> colNomeProduto;
    @FXML private TableColumn<ProdutoFeiraModel, Double> colPrecoProduto;
    @FXML private TableColumn<ProdutoFeiraModel, Integer> colQtdProduto;

    private final BarracaDAO barracaDAO = new BarracaDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ProdutoFeiraDAO produtoFeiraDAO = new ProdutoFeiraDAO();

    private ObservableList<FuncionarioModel> funcionarios = FXCollections.observableArrayList();
    private ObservableList<ProdutoFeiraModel> produtosFeira = FXCollections.observableArrayList();
    private BarracaModel barraca;

    @FXML
    public void initialize() {
        configurarTabela();
        configurarTabelaProdutos();
        tabelaFuncionarios.setItems(funcionarios);
        tabelaProdutosFeira.setItems(produtosFeira);

        barraca = barracaDAO.buscarOuCriar();
        lblStatusBarraca.setText("Status: " + (barraca.isEstaAberta() ? "aberta" : "fechada"));

        carregarFuncionariosDoBanco();
        carregarProdutosDoBanco();
    }

    private void carregarFuncionariosDoBanco() {
        funcionarios.setAll(funcionarioDAO.listarPorBarraca(barraca.getId()));
    }

    private void carregarProdutosDoBanco() {
        produtosFeira.setAll(produtoFeiraDAO.listarPorBarraca(barraca.getId()));
    }

    private void configurarTabelaProdutos() {
        colNomeProduto.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getNome()));
        colPrecoProduto.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getPreco()));
        colQtdProduto.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getQuantidade()));
    }

    @FXML
    public void adicionarProduto() {
        String nome = txtFldNomeProdutoFeira.getText().trim();
        String precoTxt = txtFldPrecoProdutoFeira.getText().trim();
        String qtdTxt = txtFldQtdProdutoFeira.getText().trim();

        if (nome.isEmpty() || precoTxt.isEmpty() || qtdTxt.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos do produto.");
            return;
        }
        try {
            double preco = Double.parseDouble(precoTxt.replace(",", "."));
            int qtd = Integer.parseInt(qtdTxt);
            ProdutoFeiraModel p = new ProdutoFeiraModel(nome, preco, qtd);
            p.setBarracaId(barraca.getId());
            produtoFeiraDAO.salvar(p);
            produtosFeira.add(p);
            txtFldNomeProdutoFeira.clear();
            txtFldPrecoProdutoFeira.clear();
            txtFldQtdProdutoFeira.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Preço ou quantidade inválidos.");
        }
    }

    @FXML
    public void venderProdutoFeira() {
        ProdutoFeiraModel selecionado = tabelaProdutosFeira.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um produto para vender."); return; }
        if (!selecionado.vender()) { mostrarAlerta("Atenção", "Produto sem estoque."); return; }
        produtoFeiraDAO.atualizar(selecionado);
        tabelaProdutosFeira.refresh();
    }

    @FXML
    public void removerProduto() {
        ProdutoFeiraModel selecionado = tabelaProdutosFeira.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Atenção", "Selecione um produto para remover."); return; }
        produtoFeiraDAO.deletar(selecionado.getId());
        produtosFeira.remove(selecionado);
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getNome()));
        colSalario.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getSalarioPorHora()));
        colHoras.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getHorasTrabalhadasSemana()));
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

        FuncionarioModel novoFuncionario = new FuncionarioModel(nome, salario, 0);
        novoFuncionario.setBarracaId(barraca.getId());
        funcionarioDAO.salvar(novoFuncionario);

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

        funcionarioDAO.deletar(selecionado.getId());
        funcionarios.remove(selecionado);
        mostrarAlerta("Atenção", selecionado.getNome() + " foi demitido(a).");
    }

    @FXML
    public void trabalhar() {
        FuncionarioModel selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um funcionário para trabalhar.");
            return;
        }

        selecionado.trabalhar(8);
        funcionarioDAO.atualizarHoras(selecionado);
        tabelaFuncionarios.refresh();
    }

    @FXML
    public void pagarSalario() {
        FuncionarioModel selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um funcionário para pagar.");
            return;
        }
        if (selecionado.getHorasTrabalhadasSemana() == 0) {
            mostrarAlerta("Atenção", "Funcionário não tem horas trabalhadas a receber.");
            return;
        }

        mostrarAlerta("Atenção", selecionado.getNome() + " recebeu R$" + selecionado.receberPagamento());
        selecionado.setHorasTrabalhadasSemana(0);
        funcionarioDAO.atualizarHoras(selecionado);
        tabelaFuncionarios.refresh();
    }

    @FXML
    public void abrirBarraca() {
        String resultado = barraca.abrirBarraca();
        barracaDAO.atualizar(barraca);
        lblStatusBarraca.setText("Status: " + resultado);
    }

    private void limparCampos() {
        txtFldNomeFuncionario.clear();
        txtFldSalarioFuncionario.clear();
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
