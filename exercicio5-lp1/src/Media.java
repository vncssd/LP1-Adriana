public class Media {

    private double p1;
    private double e1;
    private double e2;
    private double x;
    private double sub;
    private double api;
    private double exf;

    public Media(double p1, double e1, double e2, double sub, double api, double exf) {
        this.p1 = p1;
        this.e1 = e1;
        this.e2 = e2;
        this.x = x;
        this.sub = sub;
        this.api = api;
        this.exf = exf;
    }

    public Media() {
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getExf() {
        return exf;
    }

    public void setExf(double exf) {
        this.exf = exf;
    }

    public double getE1() {
        return e1;
    }

    public void setE1(double e1) {
        this.e1 = e1;
    }

    public double getSub() {
        return sub;
    }

    public void setSub(double sub) {
        this.sub = sub;
    }

    public double getE2() {
        return e2;
    }

    public void setE2(double e2) {
        this.e2 = e2;
    }

    public double getApi() {
        return api;
    }

    public void setApi(double api) {
        this.api = api;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double calcularNota (double p1,
                                double e1,
                                double e2,
                                double sub,
                                double api,
                                double x,
                                double exf){
        double max = (p1*0.5+e1*0.2+e2*0.3+x+sub*0.15);

        if (max > 5.9){
            double notaFinal = (max*0.5)+api*0.5;
            return Math.min(notaFinal, 10.0);
        }

        if (max<exf){
            return exf;
        }
        return max*0.5;
    }

}
