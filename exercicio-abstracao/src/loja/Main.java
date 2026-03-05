package loja;

public class Main {

    static void main() {

        Loja loja = new Loja();

        Produto produto1 = new Produto("Lenço umedecido", 10.99, 24, 0);
        Produto produto2 = new Produto("Paracetamol", 11.99, 13, 0);

        loja.setBalanco(0.0);
        loja.adicionarProduto(produto1);
        loja.adicionarProduto(produto2);

        loja.listarProdutos();

        System.out.println("quantidade de produtos correspondentes: " + loja.encontrarProdutos("Paracetamol"));
        System.out.println("BALANÇO: " + loja.getBalanco());
        loja.venderProduto(produto1, 2);
        System.out.println("BALANÇO: " + loja.getBalanco());

        produto1.promocao(50.0);
        loja.venderProduto(produto1, 2);
        System.out.println("BALANÇO: " + loja.getBalanco());
        produto1.promocao(10.0);
        produto2.aumento(20.0);

        loja.listarProdutos();

        System.out.println();
        System.out.println(produto1.gerarRelatorio(produto1));

    }

}
