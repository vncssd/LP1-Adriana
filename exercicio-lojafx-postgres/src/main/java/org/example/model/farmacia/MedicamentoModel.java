package org.example.model.farmacia;

import org.example.enums.farmacia.TipoMedicamento;

public class MedicamentoModel {

    private Integer id;
    private Integer farmaciaId;
    private String nome;
    private Double preco;
    private Integer quantidadeEmEstoque;
    private Integer quantidadeVendida;
    private TipoMedicamento tipo;

    public MedicamentoModel() {}

    public MedicamentoModel(String nome, Double preco, Integer quantidadeEmEstoque, TipoMedicamento tipo) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.quantidadeVendida = 0;
        this.tipo = tipo;
    }

    public Integer getId()                              { return id; }
    public void setId(Integer id)                       { this.id = id; }
    public Integer getFarmaciaId()                      { return farmaciaId; }
    public void setFarmaciaId(Integer farmaciaId)       { this.farmaciaId = farmaciaId; }
    public String getNome()                             { return nome; }
    public void setNome(String nome)                    { this.nome = nome; }
    public Double getPreco()                            { return preco; }
    public void setPreco(Double preco)                  { this.preco = preco; }
    public Integer getQuantidadeEmEstoque()             { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(Integer q)       { this.quantidadeEmEstoque = q; }
    public Integer getQuantidadeVendida()               { return quantidadeVendida; }
    public void setQuantidadeVendida(Integer q)         { this.quantidadeVendida = q; }
    public TipoMedicamento getTipo()                    { return tipo; }
    public void setTipo(TipoMedicamento tipo)           { this.tipo = tipo; }

    // método 1 — vender
    public boolean vender() {
        if (quantidadeEmEstoque <= 0) return false;
        quantidadeEmEstoque--;
        quantidadeVendida++;
        return true;
    }

    // método 2 — aplicar promoção
    public void aplicarPromocao(Double porcentagem) {
        preco = preco - (preco * (porcentagem / 100.0));
    }

    // método 3 — reabastecer
    public void reabastecer(int qtd) {
        quantidadeEmEstoque += qtd;
    }
}
