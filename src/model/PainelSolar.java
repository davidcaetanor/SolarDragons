package model;

public class PainelSolar {
    private double potencia;
    private double preco;

    public PainelSolar(double potencia, double preco) {
        this.potencia = potencia;
        this.preco = preco;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
