package org.example.model.loja;

public class ProdutoModel {

    private Integer id;
    private String nome;
    private Double preco;
    private Integer quantidadeEmEstoque;
    private Integer quantidadeVendida;

    public ProdutoModel() {}

    public ProdutoModel(String nome, Double preco, Integer quantidadeEmEstoque, Integer quantidadeVendida) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.quantidadeVendida = quantidadeVendida;
    }

    // getters / setters
    public Integer getId()                            { return id; }
    public void setId(Integer id)                     { this.id = id; }
    public String getNome()                           { return nome; }
    public void setNome(String nome)                  { this.nome = nome; }
    public Double getPreco()                          { return preco; }
    public void setPreco(Double preco)                { this.preco = preco; }
    public Integer getQuantidadeEmEstoque()           { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(Integer q)     { this.quantidadeEmEstoque = q; }
    public Integer getQuantidadeVendida()             { return quantidadeVendida; }
    public void setQuantidadeVendida(Integer q)       { this.quantidadeVendida = q; }

    public String gerarRelatorio(ProdutoModel produto) {
        return "PRODUTO: " + produto.getNome() +
               "\nQUANTIDADE VENDIDA: " + produto.getQuantidadeVendida();
    }

    public void promocao(Double porcentagem) {
        setPreco(preco - (preco * (porcentagem * 0.01)));
    }

    public void aumento(Double porcentagem) {
        setPreco(preco + (preco * (porcentagem * 0.01)));
    }
}
