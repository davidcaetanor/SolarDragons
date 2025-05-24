package view;

import model.Cliente;
import model.SimulacaoEnergia;
import service.SessaoUsuario;
import service.ServicoCadastroCliente;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GraficoEconomia {

    public static void exibirGraficoEconomia() {

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        List<Cliente> clientes = ServicoCadastroCliente.listarClientesDoUsuario(cpfUsuario);


        List<Cliente> comSimulacao = clientes.stream()
                .filter(c -> c.getSimulacao() != null)
                .collect(Collectors.toList());

        if (comSimulacao.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você precisa fazer ao menos uma simulação para visualizar o gráfico!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }


        List<String> nomesClientes = comSimulacao.stream()
                .map(c -> c.getNome() + " (" + c.getCpf().substring(0, 3) + "...)")
                .collect(Collectors.toList());

        List<Double> economia5Anos = comSimulacao.stream()
                .map(c -> c.getSimulacao().getEconomiaAnual() * 5)
                .collect(Collectors.toList());

        List<Double> investimentoInicial = comSimulacao.stream()
                .map(c -> c.getSimulacao().getCustoSistema())
                .collect(Collectors.toList());

        List<String> paybacks = comSimulacao.stream()
                .map(c -> formatarPayback(c.getSimulacao().getPaybackAnos()))
                .collect(Collectors.toList());


        CategoryChart chart = new CategoryChartBuilder()
                .width(950)
                .height(620)
                .title("Energia Solar: Economia x Investimento (em 5 anos)")
                .xAxisTitle("Clientes Simulados")
                .yAxisTitle("Valor (R$)")
                .build();

        CategoryStyler styler = chart.getStyler();
        styler.setLegendPosition(Styler.LegendPosition.InsideNE);
        styler.setYAxisDecimalPattern("'R$' #,##0.00");
        styler.setLocale(Locale.forLanguageTag("pt-BR"));
        styler.setAvailableSpaceFill(0.6);
        styler.setStacked(false);
        styler.setPlotGridVerticalLinesVisible(false);
        styler.setPlotGridHorizontalLinesVisible(true);

        styler.setSeriesColors(new Color[]{
                new Color(34, 139, 34),
                new Color(178, 34, 34)
        });

        chart.addSeries("Economia em 5 anos", nomesClientes, economia5Anos);
        chart.addSeries("Investimento Inicial", nomesClientes, investimentoInicial);


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gráfico - Economia x Investimento");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel painelChart = new XChartPanel<>(chart);
            frame.add(painelChart, BorderLayout.CENTER);


            JTextArea texto = new JTextArea();
            texto.setEditable(false);
            texto.setBackground(new Color(242, 242, 242));
            texto.setText(
                    "Investir em energia solar pode ser mais acessível do que você imagina!\n" +
                            "Veja como, em apenas alguns anos, sua economia supera o valor investido.\n\n" +
                            "Payback dos clientes simulados:\n" +
                            paybacksMensagem(comSimulacao, paybacks)
            );
            texto.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            frame.add(texto, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";
        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);
        if (anosInt == 0 && meses == 0) return "Menos de 1 mês";
        if (anosInt == 0) return meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return anosInt + (anosInt == 1 ? " ano" : " anos");
        return anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }

    private static String paybacksMensagem(List<Cliente> clientes, List<String> paybacks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clientes.size(); i++) {
            sb.append(clientes.get(i).getNome())
                    .append(": ")
                    .append(paybacks.get(i))
                    .append("\n");
        }
        return sb.toString();
    }
}
