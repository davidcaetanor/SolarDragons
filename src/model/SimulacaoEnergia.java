package model;

import java.util.HashMap;
import java.util.Map;

public class SimulacaoEnergia {
    private double valorContaReais;
    private double tarifa;
    private double consumoEstimadoKwh;
    private double geracaoEstimadaKwh;
    private double economiaAnual;
    private double potenciaSistemaKw;
    private int quantidadeModulos;
    private double areaNecessariaM2;
    private double custoSistema;

    private static final Map<String, Double> tarifaPorEstado = new HashMap<>() {{
        put("SP", 0.89);
        put("MG", 0.88);
        put("RJ", 0.92);
        put("BA", 0.93);
        put("PR", 0.87);
        put("AM", 0.94);
        put("PA", 0.91);
    }};

    public SimulacaoEnergia(String estado, double valorContaReais) {
        this.valorContaReais = valorContaReais;
        this.tarifa = tarifaPorEstado.getOrDefault(estado.toUpperCase(), 0.90);
        this.consumoEstimadoKwh = valorContaReais / tarifa;
        this.geracaoEstimadaKwh = consumoEstimadoKwh * 0.95;

        this.economiaAnual = geracaoEstimadaKwh * tarifa * 12;

        this.potenciaSistemaKw = consumoEstimadoKwh / 108.0;
        this.quantidadeModulos = (int) Math.ceil(potenciaSistemaKw / 0.555);
        this.areaNecessariaM2 = quantidadeModulos * 3.375;
        this.custoSistema = quantidadeModulos * 1390.73;
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
