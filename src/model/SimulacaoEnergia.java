package model;

import java.util.HashMap;
import java.util.Map;

public class SimulacaoEnergia {
    private static final double CUSTO_FIXO = 3000.0;
    private static final double CUSTO_POR_KWP = 5000.0;
    private static final double FATOR_ECONOMIA = 0.8;
    private static final double PERCENTUAL_GERACAO = 0.95;
    private static final double POTENCIA_MODULO_KW = 0.555;
    private static final double AREA_MODULO_M2 = 2.0;

    private static final Map<String, Double> tarifaPorEstado = new HashMap<>() {{
        put("AC", 1.10);
        put("AL", 1.15);
        put("AM", 1.25);
        put("AP", 1.12);
        put("BA", 1.14);
        put("CE", 1.07);
        put("DF", 1.10);
        put("ES", 1.09);
        put("GO", 1.17);
        put("MA", 1.10);
        put("MT", 1.13);
        put("MS", 1.13);
        put("MG", 1.07);
        put("PA", 1.15);
        put("PB", 1.09);
        put("PR", 0.98);
        put("PE", 1.10);
        put("PI", 1.06);
        put("RJ", 1.15);
        put("RN", 1.14);
        put("RO", 1.22);
        put("RR", 1.18);
        put("RS", 1.10);
        put("SC", 0.99);
        put("SE", 1.13);
        put("SP", 1.07);
        put("TO", 1.13);
    }};
    private final double valorContaReais;
    private final double tarifa;
    private final double consumoEstimadoKwh;
    private final double geracaoEstimadaKwh;
    private final double economiaMensal;
    private final double economiaAnual;
    private final double potenciaSistemaKw;
    private final int quantidadeModulos;
    private final double areaNecessariaM2;
    private final double custoSistema;

    private int paybackMeses = -1;


    public SimulacaoEnergia(String estado, double valorContaReais) {
        this.valorContaReais = valorContaReais;
        this.tarifa = tarifaPorEstado.getOrDefault(estado.toUpperCase(), 1.10);
        this.consumoEstimadoKwh = valorContaReais / this.tarifa;
        this.geracaoEstimadaKwh = consumoEstimadoKwh * PERCENTUAL_GERACAO;
        double producaoMediaKwhPorKwp = 135.0;
        this.potenciaSistemaKw = geracaoEstimadaKwh / producaoMediaKwhPorKwp;
        this.quantidadeModulos = (int) Math.ceil(potenciaSistemaKw / POTENCIA_MODULO_KW);
        this.areaNecessariaM2 = quantidadeModulos * AREA_MODULO_M2;
        this.custoSistema = CUSTO_FIXO + (potenciaSistemaKw * CUSTO_POR_KWP);
        this.economiaMensal = (this.tarifa * this.geracaoEstimadaKwh) * FATOR_ECONOMIA;
        this.economiaAnual = economiaMensal * 12;

        this.paybackMeses = (int) Math.round(getPaybackAnos() * 12);
    }


    public SimulacaoEnergia(String estado, double valorContaReais, int paybackMeses) {
        this(estado, valorContaReais);
        this.paybackMeses = paybackMeses;
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

    public double getEconomiaMensal() {
        return economiaMensal;
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

    public int getPaybackMeses() {
        return paybackMeses;
    }

    public void setPaybackMeses(int paybackMeses) {
        this.paybackMeses = paybackMeses;
    }

    public double getPaybackAnos() {
        if (economiaAnual <= 0) return -1;
        return custoSistema / economiaAnual;
    }
}
