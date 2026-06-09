package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.loja.ProdutoDAO;
import org.example.model.loja.ProdutoModel;

import java.io.IOException;

public class LojaController {

    @FXML private TextField txtFldNomeProdutoLoja;
    @FXML private TextField txtFldPrecoProdutoLoja;
    @FXML private Spinner<Integer> spinnerQuantidade;
    @FXML private TableView<ProdutoModel> tabelaProdutos;
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, Double> colPreco;
    @FXML private TableColumn<ProdutoModel, Integer> colEstoque;
    @FXML private TableColumn<ProdutoModel, Integer> colVendida;
    @FXML private Label lblBalanco;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private ObservableList<ProdutoModel> produtos = FXCollections.observableArrayList();
    private double balanco = 0.0;

    @FXML
    public void initialize() {
        configurarTabela();
        tabelaProdutos.setItems(produtos);

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 1);
        spinnerQuantidade.setValueFactory(valueFactory);

        carregarProdutosDoBanco();
        atualizarBalanco();
    }

    private void carregarProdutosDoBanco() {
        produtos.setAll(produtoDAO.listarTodos());
        // recalcula balanço a partir dos dados persistidos
        balanco = produtos.stream()
                .mapToDouble(p -> p.getPreco() * p.getQuantidadeVendida())
                .sum();
        atualizarBalanco();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(cd.getValue().getNome()));
        colPreco.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getPreco()));
        colEstoque.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getQuantidadeEmEstoque()));
        colVendida.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getQuantidadeVendida()));
    }

    @FXML
    public void cadastrarProduto() {
        String nome = txtFldNomeProdutoLoja.getText().trim();
        String precoTexto = txtFldPrecoProdutoLoja.getText().trim();

        if (nome.isEmpty() || precoTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Preço inválido.");
            return;
        }

        int quantidade = spinnerQuantidade.getValue();

        // verifica se já existe no banco
        ProdutoModel existente = produtoDAO.buscarPorNome(nome);
        if (existente != null) {
            existente.setQuantidadeEmEstoque(existente.getQuantidadeEmEstoque() + quantidade);
            produtoDAO.atualizar(existente);
        } else {
            ProdutoModel novo = new ProdutoModel(nome, preco, quantidade, 0);
            produtoDAO.salvar(novo);
        }

        carregarProdutosDoBanco();
        limparCampos();
    }

    @FXML
    public void venderProduto() {
        ProdutoModel selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Atenção", "Selecione um produto para vender.");
            return;
        }
        if (selecionado.getQuantidadeEmEstoque() <= 0) {
            mostrarAlerta("Atenção", "Produto sem estoque.");
            return;
        }

        selecionado.setQuantidadeEmEstoque(selecionado.getQuantidadeEmEstoque() - 1);
        selecionado.setQuantidadeVendida(selecionado.getQuantidadeVendida() + 1);
        produtoDAO.atualizar(selecionado);

        balanco += selecionado.getPreco();
        tabelaProdutos.refresh();
        atualizarBalanco();
    }

    private void atualizarBalanco() {
        lblBalanco.setText(String.format("Balanço: R$ %.2f", balanco));
    }

    private void limparCampos() {
        txtFldNomeProdutoLoja.clear();
        txtFldPrecoProdutoLoja.clear();
        spinnerQuantidade.getValueFactory().setValue(1);
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
