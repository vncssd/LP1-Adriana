package barraca;

public class Main {

    static void main(String[] args) {

        Barraca barraca = new Barraca();
        barraca.setHorasDeExpediente(4);

        Funcionario funcionario1 = new Funcionario("Gabriel Agreste", 50.0, 20);
        Funcionario funcionario2 = new Funcionario("Marinete Dupain-Cheng ", 20.0, 30);
        Funcionario funcionario3 = new Funcionario("Adrien Agreste", 10.0, 15);
        Funcionario funcionario4 = new Funcionario("Chloé Bourgeois", 20.0, 15);

        barraca.contratarFuncionario(funcionario1);
        barraca.contratarFuncionario(funcionario2);
        barraca.contratarFuncionario(funcionario3);
        barraca.contratarFuncionario(funcionario4);

        funcionario1.trabalhar(2);
        funcionario2.trabalhar(2);
        funcionario3.trabalhar(2);
        funcionario4.trabalhar(2);

        barraca.abrirBarraca();

        funcionario1.receberPagamento();
        funcionario2.receberPagamento();
        funcionario3.receberPagamento();
        funcionario4.receberPagamento();
        System.out.println();

        barraca.demitirFuncionario("Chloé Bourgeois");
        System.out.println();

        funcionario1.gerarRelatorioFuncionario();
        funcionario2.gerarRelatorioFuncionario();


    }
}
