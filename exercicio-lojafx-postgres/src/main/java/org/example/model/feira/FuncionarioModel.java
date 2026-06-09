package org.example.model.feira;

public class FuncionarioModel {

    private Integer id;
    private Integer barracaId;
    private String nome;
    private double salarioPorHora;
    private int horasTrabalhadasSemana;

    public FuncionarioModel() {}

    public FuncionarioModel(String nome, double salarioPorHora, int horasTrabalhadas) {
        this.nome = nome;
        this.salarioPorHora = salarioPorHora;
        this.horasTrabalhadasSemana = horasTrabalhadas;
    }

    // getters / setters
    public Integer getId()                                 { return id; }
    public void setId(Integer id)                          { this.id = id; }
    public Integer getBarracaId()                          { return barracaId; }
    public void setBarracaId(Integer barracaId)            { this.barracaId = barracaId; }
    public String getNome()                                { return nome; }
    public void setNome(String nome)                       { this.nome = nome; }
    public double getSalarioPorHora()                      { return salarioPorHora; }
    public void setSalarioPorHora(double salarioPorHora)   { this.salarioPorHora = salarioPorHora; }
    public int getHorasTrabalhadasSemana()                 { return horasTrabalhadasSemana; }
    public void setHorasTrabalhadasSemana(int h)           { this.horasTrabalhadasSemana = h; }

    public void trabalhar(int horas)          { horasTrabalhadasSemana += horas; }
    public Double receberPagamento()          { return horasTrabalhadasSemana * salarioPorHora; }
    public boolean compararNome(String nome)  { return nome.equals(this.nome); }

    public void gerarRelatorioFuncionario() {
        System.out.println("funcionário: " + nome + " horas trabalhadas: " + horasTrabalhadasSemana);
    }
}
