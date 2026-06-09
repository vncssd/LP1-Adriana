package org.example.model.barbearia;

import java.util.LinkedList;
import java.util.List;

public class BarbeariaModel {

    private Integer id;
    private List<EquipamentoModel> equipamentos = new LinkedList<>();
    private boolean estaAberto;
    private boolean possuiEquipamentosSemManutencao;

    public BarbeariaModel() {}

    public BarbeariaModel(List<EquipamentoModel> equipamentos, boolean estaAberto,
                          boolean possuiEquipamentosSemManutencao) {
        this.equipamentos = equipamentos;
        this.estaAberto = estaAberto;
        this.possuiEquipamentosSemManutencao = possuiEquipamentosSemManutencao;
    }

    public Integer getId()                                            { return id; }
    public void setId(Integer id)                                     { this.id = id; }
    public List<EquipamentoModel> getEquipamentos()                   { return equipamentos; }
    public boolean isEstaAberto()                                     { return estaAberto; }
    public void setEstaAberto(boolean estaAberto)                     { this.estaAberto = estaAberto; }
    public boolean possuiEquipamentosSemManutencao()                  { return possuiEquipamentosSemManutencao; }
    public void setpossuiEquipamentosSemManutencao(boolean v)         { this.possuiEquipamentosSemManutencao = v; }

    public void abrir()  { setEstaAberto(true);  System.out.println("BARBEARIA ABERTA"); }
    public void fechar() { setEstaAberto(false); System.out.println("BARBEARIA FECHADA"); }

    public void registrarEquipamento(EquipamentoModel equipamento) { equipamentos.add(equipamento); }

    public void usarEquipamento(EquipamentoModel equipamento) {
        if (equipamento.buscarEquipamentoPorNome(equipamento)) {
            System.out.println("USANDO " + equipamento.getMarca() + " " + equipamento.getModelo());
            equipamento.setRecebeuManuntencao(false);
        }
    }

    public void checarManutencaoEquipamentos() {
        int com = 0, sem = 0;
        for (EquipamentoModel eq : equipamentos) {
            if (eq.isRecebeuManuntencao()) { com++; }
            else {
                sem++;
                System.out.println(eq.getMarca() + " " + eq.getModelo() + " precisa de manutenção!!!");
                setpossuiEquipamentosSemManutencao(true);
            }
        }
        System.out.println("EQUIPAMENTOS MANUTENIDOS: " + com);
        System.out.println("EQUIPAMENTOS SEM MANUTENÇÃO: " + sem);
    }
}
