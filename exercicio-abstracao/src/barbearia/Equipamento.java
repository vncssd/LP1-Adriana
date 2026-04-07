package barbearia;

public class Equipamento {

    private boolean recebeuManuntencao;
    private String modelo;
    private String marca;
    private EquipamentoEstado equipamentoEstado;

    public Equipamento() {
    }

    public Equipamento(boolean recebeuManuntencao, String modelo, String marca, EquipamentoEstado equipamentoEstado) {
        this.recebeuManuntencao = recebeuManuntencao;
        this.modelo = modelo;
        this.marca = marca;
        this.equipamentoEstado = equipamentoEstado;
    }

    public boolean isRecebeuManuntencao() {
        return recebeuManuntencao;
    }

    public void setRecebeuManuntencao(boolean recebeuManuntencao) {
        this.recebeuManuntencao = recebeuManuntencao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setEquipamentoEstado(EquipamentoEstado equipamentoEstado) {
        this.equipamentoEstado = equipamentoEstado;
    }

    public EquipamentoEstado getEquipamentoEstado() {
        return equipamentoEstado;
    }

    public boolean buscarEquipamentoPorNome(Equipamento equipamento){
        return equipamento.getMarca().toLowerCase().equals(getMarca().toLowerCase())
                && equipamento.getModelo().toLowerCase().equals(getModelo().toLowerCase());
    }

    public void receberManutencao(){
        setRecebeuManuntencao(true);
    }

    public void ligar(){
        setEquipamentoEstado(EquipamentoEstado.LIGADO);
        System.out.println(getMarca() + " " + getModelo() + " está " + getEquipamentoEstado());
    }

    public void desligar(){
        setEquipamentoEstado(EquipamentoEstado.DESLIGADO);
        System.out.println(getMarca() + " " + getModelo() + " está " + getEquipamentoEstado());
    }


}
