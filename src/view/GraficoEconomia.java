package view;

import model.Cliente;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GraficoEconomia {

    public static void exibirGraficoEconomia(List<Cliente> clientes) {
        List<Cliente> comSimulacao = clientes.stream()
                .filter(c -> c.getSimulacao() != null)
                .toList();

        List<String> perfis = comSimulacao.stream()
                .map(c -> "R$ " + (int) c.getSimulacao().getValorContaReais())
                .collect(Collectors.toList());

        List<Double> economia5Anos = comSimulacao.stream()
                .map(c -> c.getSimulacao().getEconomiaAnual() * 5)
                .collect(Collectors.toList());

        List<Double> investimentoInicial = comSimulacao.stream()
                .map(c -> c.getSimulacao().getCustoSistema())
                .collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(900)
                .height(600)
                .title("Ganhos com Energia Solar em 5 Anos")
                .xAxisTitle("Perfil de Conta Mensal")
                .yAxisTitle("Valor (R$)")
                .build();

        CategoryStyler styler = chart.getStyler();
        styler.setLegendPosition(Styler.LegendPosition.InsideNE);
        styler.setYAxisDecimalPattern("'R$' #,##0.00");
        styler.setLocale(Locale.forLanguageTag("pt-BR"));
        styler.setAvailableSpaceFill(0.6);
        styler.setStacked(false);
        styler.setSeriesColors(new Color[]{
                new Color(34, 139, 34),
                new Color(178, 34, 34)
        });
        styler.setPlotGridVerticalLinesVisible(false);
        styler.setPlotGridHorizontalLinesVisible(true);

        chart.addSeries("Economia em 5 Anos", perfis, economia5Anos);
        chart.addSeries("Investimento Inicial", perfis, investimentoInicial);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gráfico - Economia x Investimento");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
            frame.add(chartPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
