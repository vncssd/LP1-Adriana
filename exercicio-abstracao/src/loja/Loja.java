package loja;

import java.util.LinkedList;
import java.util.List;

public class Loja {

    private List<Produto> estoque = new LinkedList<>();
    private Double balanco;
    private Produto produto;

    public Loja() {
    }

    public Loja(List<Produto> estoque, Double balanco, Produto produto) {
        this.estoque = estoque;
        this.balanco = balanco;
        this.produto = produto;
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<Produto> estoque) {
        this.estoque = estoque;
    }

    public Double getBalanco() {
        return balanco;
    }

    public void setBalanco(Double balanco) {
        this.balanco = balanco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void adicionarProduto(Produto produto){
        estoque.add(produto);
    }

    public void removerProduto(Produto produto){
        estoque.remove(produto);
    }

    public void venderProduto(Produto produto, int quantidade){
        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque()-quantidade);
        setBalanco(getBalanco()+produto.getPreco());
    }

    public int encontrarProdutos(String nome){
        List<Produto> produtosEncontrados = new LinkedList<>();
        for(Produto produto:estoque){
            if (produto.getNome().equals(nome)){
                produtosEncontrados.add(produto);
            }
        }
        return produtosEncontrados.size();
    }

    public void listarProdutos(){
        for (Produto produto:estoque){
            System.out.println("produto: " + produto.getNome() + " R$" + produto.getPreco() + " em estoque: " + produto.getQuantidadeEmEstoque());
        }
    }

}
