package view;

import model.Cliente;
import model.SimulacaoEnergia;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraficoEconomia {

    public static void exibirGraficoEconomia(List<Cliente> clientes) {
        List<Cliente> comSimulacao = clientes.stream()
                .filter(c -> c.getSimulacao() != null)
                .toList();

        if (comSimulacao.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente com simulação disponível para gráfico.");
            return;
        }

        List<String> nomes = comSimulacao.stream()
                .map(Cliente::getNome)
                .collect(Collectors.toList());

        List<Double> economias10Anos = comSimulacao.stream()
                .map(c -> c.getSimulacao().getEconomiaAnual() * 10)
                .collect(Collectors.toList());

        List<Double> investimentos = comSimulacao.stream()
                .map(c -> c.getSimulacao().getCustoSistema())
                .collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Economia em 10 Anos vs Investimento Inicial")
                .xAxisTitle("Cliente")
                .yAxisTitle("Valor (R$)")
                .build();

        chart.addSeries("Economia em 10 Anos", nomes, economias10Anos);
        chart.addSeries("Investimento Inicial", nomes, investimentos);

        JFrame frame = new JFrame("Gráfico - Economia Projetada");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
