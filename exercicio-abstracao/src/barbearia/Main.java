package barbearia;

public class Main {

    static void main(String[] args) {

        Barbearia barbearia = new Barbearia();

        Equipamento shaver = new Equipamento(true, "Shaver", "Kimei", EquipamentoEstado.DESLIGADO);
        Equipamento maquininha = new Equipamento(true, "MT1020", "Barber PRO", EquipamentoEstado.DESLIGADO);
        Equipamento maquininhaDragao = new Equipamento(true, "GTY1092", "MODERN Barber", EquipamentoEstado.DESLIGADO);

        barbearia.registrarEquipamento(shaver);
        barbearia.registrarEquipamento(maquininha);
        barbearia.registrarEquipamento(maquininhaDragao);

        barbearia.abrir();

        barbearia.checarManutencaoEquipamentos();

        shaver.ligar();

        barbearia.usarEquipamento(shaver);

        shaver.desligar();

        barbearia.checarManutencaoEquipamentos();

        shaver.receberManutencao();

        barbearia.checarManutencaoEquipamentos();

        barbearia.fechar();


    }
}
