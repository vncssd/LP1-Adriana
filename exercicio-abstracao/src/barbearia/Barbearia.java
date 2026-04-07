package barbearia;

import java.util.LinkedList;
import java.util.List;

public class Barbearia {

    private List<Equipamento> equipamentos = new LinkedList<>();
    private boolean estaAberto;
    private boolean possuiEquipamentosSemManutencao;

    public Barbearia() {
    }

    public Barbearia(List<Equipamento> equipamentos, boolean estaAberto, boolean possuiEquipamentosSemManutencao) {
        this.equipamentos = equipamentos;
        this.estaAberto = estaAberto;
        this.possuiEquipamentosSemManutencao = possuiEquipamentosSemManutencao;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public boolean isEstaAberto() {
        return estaAberto;
    }

    public void setEstaAberto(boolean estaAberto) {
        this.estaAberto = estaAberto;
    }

    public boolean possuiEquipamentosSemManutencao() {
        return possuiEquipamentosSemManutencao;
    }

    public void setpossuiEquipamentosSemManutencao(boolean possuiEquipamentosSemManutencao) {
        this.possuiEquipamentosSemManutencao = possuiEquipamentosSemManutencao;
    }


    public void abrir(){
        setEstaAberto(true);
        System.out.println("BARBEARIA ABERTA");
    }

    public void fechar(){
        setEstaAberto(false);
        System.out.println("BARBEARIA FECHADA");
    }

    public void registrarEquipamento(Equipamento equipamento){
        equipamentos.add(equipamento);
    }

    public void usarEquipamento(Equipamento equipamento){
        if(equipamento.buscarEquipamentoPorNome(equipamento)){
            System.out.println("USANDO " + equipamento.getMarca() + " " + equipamento.getModelo());
            equipamento.setRecebeuManuntencao(false);
        }
    }

    public void checarManutencaoEquipamentos(){
        int equipamentosManutenidos = 0;
        int equipamentosSemManutencao = 0;
        for(Equipamento equipamento: equipamentos){
            if(equipamento.isRecebeuManuntencao()){
                equipamentosManutenidos++;
            }
            else{
                equipamentosSemManutencao++;
                System.out.println(equipamento.getMarca() + " " + equipamento.getModelo() + " precisa de manutenção!!!" );
                setpossuiEquipamentosSemManutencao(true);
            }
        }
        System.out.println();
        System.out.println("EQUIPAMENTOS MANUTENIDOS: " + equipamentosManutenidos);
        System.out.println("EQUIPAMENTOS SEM MANUTENÇÃO: " + equipamentosSemManutencao);
    }
}
