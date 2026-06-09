package org.example.model.barbearia;

import org.example.enums.barbearia.EquipamentoEstado;

public class EquipamentoModel {

    private Integer id;
    private Integer barbeariaId;
    private boolean recebeuManutencao;
    private String modelo;
    private String marca;
    private EquipamentoEstado equipamentoEstado;

    public EquipamentoModel() {}

    public EquipamentoModel(boolean recebeuManutencao, String modelo, String marca,
                            EquipamentoEstado equipamentoEstado) {
        this.recebeuManutencao = recebeuManutencao;
        this.modelo = modelo;
        this.marca = marca;
        this.equipamentoEstado = equipamentoEstado;
    }

    public Integer getId()                                        { return id; }
    public void setId(Integer id)                                 { this.id = id; }
    public Integer getBarbeariaId()                               { return barbeariaId; }
    public void setBarbeariaId(Integer barbeariaId)               { this.barbeariaId = barbeariaId; }
    public boolean isRecebeuManuntencao()                         { return recebeuManutencao; }
    public void setRecebeuManuntencao(boolean recebeuManutencao)  { this.recebeuManutencao = recebeuManutencao; }
    public String getModelo()                                     { return modelo; }
    public void setModelo(String modelo)                          { this.modelo = modelo; }
    public String getMarca()                                      { return marca; }
    public void setMarca(String marca)                            { this.marca = marca; }
    public EquipamentoEstado getEquipamentoEstado()               { return equipamentoEstado; }
    public void setEquipamentoEstado(EquipamentoEstado e)         { this.equipamentoEstado = e; }

    public boolean buscarEquipamentoPorNome(EquipamentoModel e) {
        return e.getMarca().equalsIgnoreCase(getMarca()) && e.getModelo().equalsIgnoreCase(getModelo());
    }

    public void receberManutencao() { setRecebeuManuntencao(true); }

    public void ligar() {
        setEquipamentoEstado(EquipamentoEstado.LIGADO);
        System.out.println(getMarca() + " " + getModelo() + " está " + getEquipamentoEstado());
    }

    public void desligar() {
        setEquipamentoEstado(EquipamentoEstado.DESLIGADO);
        System.out.println(getMarca() + " " + getModelo() + " está " + getEquipamentoEstado());
    }
}
