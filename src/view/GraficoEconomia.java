package view;

import model.Cliente;
import model.SimulacaoEnergia;
import database.ClienteDAO;
import database.SimulacaoEnergiaDAO;
import service.SessaoUsuario;
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

public class GraficoEconomia extends JFrame {

    public GraficoEconomia() {
        setTitle("Gráfico - Economia x Investimento (5 anos)");
        setMinimumSize(new Dimension(1200, 1200));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.gridx = 0;

        JLabel logo = EstiloSolarDragons.criarLogo(120, 120, "/resources/iconSolarDragons.png");
        c.gridy = 0;
        add(logo, c);

        JLabel titulo = new JLabel("Energia Solar: Economia x Investimento (em 5 anos)");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        add(titulo, c);

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        ClienteDAO clienteDAO = new ClienteDAO();
        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();

        List<Cliente> clientes = clienteDAO.listarPorUsuario(cpfUsuario);

        List<Cliente> comSimulacao = clientes.stream()
                .filter(cliente -> simulacaoDAO.buscarUltimaSimulacao(cliente.getId()) != null)
                .collect(Collectors.toList());

        if (comSimulacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você precisa fazer ao menos uma simulação para visualizar o gráfico!", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        List<String> nomesClientes = comSimulacao.stream()
                .map(cli -> cli.getNome() + " (" + cli.getCpf().substring(0, 3) + "...)")
                .collect(Collectors.toList());

        List<SimulacaoEnergia> simulacoes = comSimulacao.stream()
                .map(cli -> simulacaoDAO.buscarUltimaSimulacao(cli.getId()))
                .collect(Collectors.toList());

        List<Double> economia5Anos = simulacoes.stream()
                .map(sim -> sim.getEconomiaAnual() * 5)
                .collect(Collectors.toList());

        List<Double> investimentoInicial = simulacoes.stream()
                .map(SimulacaoEnergia::getCustoSistema)
                .collect(Collectors.toList());

        List<String> paybacks = simulacoes.stream()
                .map(sim -> formatarPayback(sim.getPaybackAnos()))
                .collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(850)
                .height(480)
                .title("")
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
                new Color(44, 154, 76),
                new Color(178, 34, 34)
        });

        chart.addSeries("Economia em 5 anos", nomesClientes, economia5Anos);
        chart.addSeries("Investimento Inicial", nomesClientes, investimentoInicial);

        JPanel painelGrafico = new JPanel(new BorderLayout());
        painelGrafico.setPreferredSize(new Dimension(850, 480));
        painelGrafico.setOpaque(false);
        painelGrafico.add(new XChartPanel<>(chart), BorderLayout.CENTER);

        c.gridy = 2;
        add(painelGrafico, c);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setBackground(new Color(246, 242, 230));
        texto.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        texto.setText(
                "Investir em energia solar é garantir anos de economia.\n" +
                "Em 5 anos, o valor economizado costuma superar o investimento inicial. Veja o tempo de retorno de cada simulação:\n\n" +
                paybacksMensagem(comSimulacao, paybacks)
        );

        texto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 197, 150), 1, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));

        c.gridy = 3;
        add(texto, c);

        JPanel painelBotoes = new JPanel();
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoFechar = new JButton("Fechar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoFechar);
        botaoFechar.setPreferredSize(new Dimension(160, 36));
        painelBotoes.add(botaoFechar);

        botaoFechar.addActionListener(e -> dispose());

        c.gridy = 4;
        add(painelBotoes, c);

        setVisible(true);
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
