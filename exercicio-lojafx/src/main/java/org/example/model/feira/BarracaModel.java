package org.example.model.feira;

import java.util.LinkedList;
import java.util.List;

public class BarracaModel {
    private boolean estaAberta;
    private int horasDeExpediente;
    private List<FuncionarioModel> funcionarios = new LinkedList<>();

    public BarracaModel() {
    }

    public void setHorasDeExpediente(int horasDeExpediente) {
        this.horasDeExpediente = horasDeExpediente;
    }

    public BarracaModel(boolean estaAberta, int horasDeExpediente, List<FuncionarioModel> funcionarios) {
        this.estaAberta = estaAberta;
        this.horasDeExpediente = horasDeExpediente;
        this.funcionarios = funcionarios;
    }

    public String abrirBarraca() {
        if (estaAberta) {
            return "barraca já está aberta!";
        }
        estaAberta = true;
        return "barraca aberta";
    }

    public void contratarFuncionario(FuncionarioModel funcionario) {
        funcionarios.add(funcionario);
    }

    public void demitirFuncionario(String nome) {
        for (FuncionarioModel f : funcionarios) {
            if (f.compararNome(nome)) {
                funcionarios.remove(f);
                System.out.print("funcionário: " + nome + " demitido");
            }
        }

    }
}
