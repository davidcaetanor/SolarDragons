package model;

import java.util.HashMap;
import java.util.Map;

public class SimulacaoEnergia {
    private final double valorContaReais;
    private final double tarifa;
    private final double consumoEstimadoKwh;
    private final double geracaoEstimadaKwh;
    private final double economiaAnual;
    private final double potenciaSistemaKw;
    private final int quantidadeModulos;
    private final double areaNecessariaM2;
    private final double custoSistema;

    private static final Map<String, Double> tarifaPorEstado = new HashMap<>() {{
        put("AC", 0.89); put("AL", 0.90); put("AM", 0.94); put("AP", 0.91); put("BA", 0.93);
        put("CE", 0.88); put("DF", 0.87); put("ES", 0.89); put("GO", 0.90); put("MA", 0.92);
        put("MT", 0.86); put("MS", 0.85); put("MG", 0.88); put("PA", 0.91); put("PB", 0.89);
        put("PR", 0.87); put("PE", 0.90); put("PI", 0.89); put("RJ", 0.92); put("RN", 0.88);
        put("RO", 0.87); put("RR", 0.90); put("RS", 0.89); put("SC", 0.88); put("SE", 0.89);
        put("SP", 0.89); put("TO", 0.90);
    }};

    public SimulacaoEnergia(String estado, double valorContaReais) {
        this.valorContaReais = valorContaReais;
        this.tarifa = tarifaPorEstado.getOrDefault(estado.toUpperCase(), 0.90);
        this.consumoEstimadoKwh = valorContaReais / tarifa;
        this.geracaoEstimadaKwh = consumoEstimadoKwh * 0.95;
        this.potenciaSistemaKw = consumoEstimadoKwh / 108.0;
        this.quantidadeModulos = (int) Math.ceil(potenciaSistemaKw / 0.555);
        this.areaNecessariaM2 = quantidadeModulos * 3.375;

        double custoPorKw = 2650.00;
        this.custoSistema = potenciaSistemaKw * custoPorKw;

        double fatorRealidade = 0.75;
        this.economiaAnual = geracaoEstimadaKwh * tarifa * 12 * fatorRealidade;
    }

    public double getValorContaReais() {
        return valorContaReais;
    }

    public double getTarifa() {
        return tarifa;
    }

    public double getConsumoEstimadoKwh() {
        return consumoEstimadoKwh;
    }

    public double getGeracaoEstimadaKwh() {
        return geracaoEstimadaKwh;
    }

    public double getEconomiaAnual() {
        return economiaAnual;
    }

    public double getPotenciaSistemaKw() {
        return potenciaSistemaKw;
    }

    public int getQuantidadeModulos() {
        return quantidadeModulos;
    }

    public double getAreaNecessariaM2() {
        return areaNecessariaM2;
    }

    public double getCustoSistema() {
        return custoSistema;
    }

    public double getPaybackAnos() {
        return economiaAnual > 0 ? custoSistema / economiaAnual : -1;
    }
}
