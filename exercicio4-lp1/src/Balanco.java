public class Balanco {

    private int jan;
    private int fev;
    private int mar;

    public Balanco() {
    }

    public Balanco(int jan, int fev, int mar) {
        this.jan = jan;
        this.fev = fev;
        this.mar = mar;
    }

    public int getJan() {
        return jan;
    }

    public void setJan(int jan) {
        this.jan = jan;
    }

    public int getFev() {
        return fev;
    }

    public void setFev(int fev) {
        this.fev = fev;
    }

    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int calcularDespesaTotal(){
        int despesaTotal =  jan+fev+mar;
        return despesaTotal;
    }

    public int calcularMedia(){
        int media = (jan+fev+mar)/3;
        return media;
    }

}
