package loja;

public class Produto {

    private String nome;
    private Double preco;
    private Integer quantidadeEmEstoque;

    public Produto() {
    }

    public Produto(String nome, Double preco, Integer quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Integer getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Integer quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public String gerarRelatorio(Produto produto){
        return "PRODUTO: " + produto.getNome() +
                "\nQUANTIDADE VENDIDA: " + produto.getQuantidadeVendida();
    }

    


}
