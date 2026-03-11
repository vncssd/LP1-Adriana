package barraca;

import java.util.LinkedList;
import java.util.List;

public class Barraca {

    private boolean estaAberta;
    private int horasDeExpediente;
    private List<Funcionario> funcionarios = new LinkedList<>();

    public Barraca() {
    }

    public void setHorasDeExpediente(int horasDeExpediente) {
        this.horasDeExpediente = horasDeExpediente;
    }

    public Barraca(boolean estaAberta, int horasDeExpediente, List<Funcionario> funcionarios) {
        this.estaAberta = estaAberta;
        this.horasDeExpediente = horasDeExpediente;
        this.funcionarios = funcionarios;
    }

    public String abrirBarraca(){
        if(estaAberta){
            return "barraca já está aberta!";
        }
        estaAberta = true;
        return "barraca aberta";
    }

    public void contratarFuncionario(Funcionario funcionario){
        funcionarios.add(funcionario);
    }

    public void demitirFuncionario(String nome){
        for (Funcionario f : funcionarios){
            if (f.compararNome(nome)){
                funcionarios.remove(f);
                System.out.print("funcionário: " +nome+ " demitido");
            }
        }

    }
}
