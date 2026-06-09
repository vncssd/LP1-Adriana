package org.example.model.feira;

public class ProdutoFeiraModel {

    private Integer id;
    private Integer barracaId;
    private String nome;
    private Double preco;
    private Integer quantidade;

    public ProdutoFeiraModel() {}

    public ProdutoFeiraModel(String nome, Double preco, Integer quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Integer getId()                     { return id; }
    public void setId(Integer id)              { this.id = id; }
    public Integer getBarracaId()              { return barracaId; }
    public void setBarracaId(Integer barracaId){ this.barracaId = barracaId; }
    public String getNome()                    { return nome; }
    public void setNome(String nome)           { this.nome = nome; }
    public Double getPreco()                   { return preco; }
    public void setPreco(Double preco)         { this.preco = preco; }
    public Integer getQuantidade()             { return quantidade; }
    public void setQuantidade(Integer q)       { this.quantidade = q; }

    // método 1 — vender uma unidade
    public boolean vender() {
        if (quantidade <= 0) return false;
        quantidade--;
        return true;
    }

    // método 2 — reabastecer
    public void reabastecer(int qtd) {
        quantidade += qtd;
    }

    // método 3 — aplicar desconto
    public void aplicarDesconto(double porcentagem) {
        preco = preco - (preco * (porcentagem / 100.0));
    }
}
