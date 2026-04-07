package barbearia;

public class Equipamento {

    private boolean recebeuManuntencao;
    private String modelo;
    private String marca;
    private EquipamentoEstado equipamentoEstado;

    public Equipamento() {
    }

    public Equipamento(boolean recebeuManuntencao, String modelo, String marca) {
        this.recebeuManuntencao = recebeuManuntencao;
        this.modelo = modelo;
        this.marca = marca;
    }



}
