package org.example.model.feira;

import java.util.LinkedList;
import java.util.List;

public class BarracaModel {

    private Integer id;
    private boolean estaAberta;
    private int horasDeExpediente;
    private List<FuncionarioModel> funcionarios = new LinkedList<>();

    public BarracaModel() {}

    public BarracaModel(boolean estaAberta, int horasDeExpediente, List<FuncionarioModel> funcionarios) {
        this.estaAberta = estaAberta;
        this.horasDeExpediente = horasDeExpediente;
        this.funcionarios = funcionarios;
    }

    public Integer getId()                                 { return id; }
    public void setId(Integer id)                          { this.id = id; }
    public boolean isEstaAberta()                          { return estaAberta; }
    public void setEstaAberta(boolean estaAberta)          { this.estaAberta = estaAberta; }
    public int getHorasDeExpediente()                      { return horasDeExpediente; }
    public void setHorasDeExpediente(int h)                { this.horasDeExpediente = h; }
    public List<FuncionarioModel> getFuncionarios()        { return funcionarios; }

    public String abrirBarraca() {
        if (estaAberta) return "barraca já está aberta!";
        estaAberta = true;
        return "barraca aberta";
    }

    public void contratarFuncionario(FuncionarioModel funcionario) { funcionarios.add(funcionario); }

    public void demitirFuncionario(String nome) {
        funcionarios.removeIf(f -> f.compararNome(nome));
    }
}
