package org.example.model.barbearia;

import org.example.model.barbearia.EquipamentoModel;

import java.util.LinkedList;
import java.util.List;

public class BarbeariaModel {

    private List<EquipamentoModel> equipamentos = new LinkedList<>();
    private boolean estaAberto;
    private boolean possuiEquipamentosSemManutencao;

    public BarbeariaModel() {
    }

    public BarbeariaModel(List<EquipamentoModel> equipamentos, boolean estaAberto, boolean possuiEquipamentosSemManutencao) {
        this.equipamentos = equipamentos;
        this.estaAberto = estaAberto;
        this.possuiEquipamentosSemManutencao = possuiEquipamentosSemManutencao;
    }

    public List<EquipamentoModel> getEquipamentos() {
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

    public void registrarEquipamento(EquipamentoModel equipamento){
        equipamentos.add(equipamento);
    }

    public void usarEquipamento(EquipamentoModel equipamento){
        if(equipamento.buscarEquipamentoPorNome(equipamento)){
            System.out.println("USANDO " + equipamento.getMarca() + " " + equipamento.getModelo());
            equipamento.setRecebeuManuntencao(false);
        }
    }

    public void checarManutencaoEquipamentos(){
        int equipamentosManutenidos = 0;
        int equipamentosSemManutencao = 0;
        for(EquipamentoModel equipamento: equipamentos){
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