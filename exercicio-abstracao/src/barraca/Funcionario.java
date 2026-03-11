package barraca;

public class Funcionario {

    private String nome;
    private double salarioPorHora;
    private int horasTrabalhadasSemana;

    public Funcionario() {
    }

    public Funcionario(String nome, double salarioPorHora, int horasTrabalhadas) {
        this.nome = nome;
        this.salarioPorHora = salarioPorHora;
        this.horasTrabalhadasSemana = horasTrabalhadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalarioPorHora() {
        return salarioPorHora;
    }

    public void setSalarioPorHora(double salarioPorHora) {
        this.salarioPorHora = salarioPorHora;
    }

    public int getHorasTrabalhadasSemana() {
        return horasTrabalhadasSemana;
    }

    public void trabalhar(int horas) {
            horasTrabalhadasSemana += horas;
        }

    public void gerarRelatorioFuncionario(){
        System.out.println("funcionário: " + nome + " horas trabalhadas: " + horasTrabalhadasSemana);

    }

    public void receberPagamento(){
        Double total = horasTrabalhadasSemana *salarioPorHora;
        System.out.printf("salário pago para %s: R$%.2f %n", nome, total);
    }

    public boolean compararNome(String nome){
        if(nome.equals(this.nome)){
            return true;
        }
        return false;
    }

}
