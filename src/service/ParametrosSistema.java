package service;

public class ParametrosSistema {

    private static double custoPorKw = 2650.00;
    private static double fatorEconomia = 0.75;
    private static double percentualGeracao = 0.95; // 95%

    public static double getCustoPorKw() {
        return custoPorKw;
    }

    public static void setCustoPorKw(double valor) {
        if (valor > 0) {
            custoPorKw = valor;
        }
    }

    public static double getFatorEconomia() {
        return fatorEconomia;
    }

    public static void setFatorEconomia(double valor) {
        if (valor > 0 && valor <= 1) {
            fatorEconomia = valor;
        }
    }

    public static double getPercentualGeracao() {
        return percentualGeracao;
    }

    public static void setPercentualGeracao(double valor) {
        if (valor > 0 && valor <= 1) {
            percentualGeracao = valor;
        }
    }
}
