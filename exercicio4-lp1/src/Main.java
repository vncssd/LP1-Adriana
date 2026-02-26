public class Main {

    static void main(String[] args) {

        Balanco balanco = new Balanco(15000, 23000, 17000);

        System.out.println("total trismestre: " + balanco.calcularDespesaTotal());
        System.out.println("media de gasto: " + balanco.calcularMedia());

    }

}
