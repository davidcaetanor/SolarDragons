package view;

import model.Cliente;
import model.SimulacaoEnergia;
import database.ClienteDAO;
import database.SimulacaoEnergiaDAO;
import service.SessaoUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaSimulacaoEconomia extends JFrame {

    private JTextField campoConta;
    private JButton botaoSimular, botaoVoltar;
    private JPanel cardResultado;
    private JLabel lblConsumo, lblPotencia, lblModulos, lblArea, lblCusto, lblEconomia, lblPayback;
    private JLabel logoLabel;
    private ImageIcon logoGrande, logoPequeno;

    public TelaSimulacaoEconomia() {
        setTitle("Simulação de Economia");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12,12,12,12);
        c.gridx = 0; c.gridwidth = 2;


        logoGrande = EstiloSolarDragons.getIcon(200, 200, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        logoPequeno = EstiloSolarDragons.getIcon(90, 90, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        logoLabel = new JLabel(logoGrande);
        c.gridy = 0; c.anchor = GridBagConstraints.CENTER;
        add(logoLabel, c);

        JLabel titulo = new JLabel("Simulação de Economia Solar");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        add(titulo, c);

        c.gridy = 3; c.gridwidth = 1; c.anchor = GridBagConstraints.EAST;
        JLabel labelConta = new JLabel("Valor médio da conta (R$):");
        EstiloSolarDragons.estilizarLabel(labelConta);
        add(labelConta, c);

        campoConta = new JTextField(16);
        campoConta.setFont(new Font("Arial", Font.PLAIN, 16));
        campoConta.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        campoConta.setBackground(EstiloSolarDragons.CINZA_CAMPO);
        campoConta.setBorder(BorderFactory.createLineBorder(EstiloSolarDragons.AZUL_ESCURO, 2, true));
        campoConta.setToolTipText("Ex: 350,00");
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        add(campoConta, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        botaoSimular = new JButton("Simular");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSimular);

        botaoVoltar = new JButton("Voltar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoVoltar);

        Dimension botaoSize = new Dimension(130, 40);
        botaoSimular.setPreferredSize(botaoSize);
        botaoVoltar.setPreferredSize(botaoSize);

        painelBotoes.add(botaoSimular);
        painelBotoes.add(botaoVoltar);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        add(painelBotoes, c);

        cardResultado = criarPainelResultado();
        cardResultado.setVisible(false);
        c.gridy = 6; c.insets = new Insets(35,16,16,16);
        add(cardResultado, c);

        botaoSimular.addActionListener(e -> simularEconomia());
        botaoVoltar.addActionListener(e -> {
            TelaUtil.voltarParaPainelUsuario(this);
        });

        setVisible(true);
    }

    private JPanel criarPainelResultado() {
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 197, 150), 2, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        card.setPreferredSize(new Dimension(500, 370));
        card.setOpaque(true);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,8,0,new Color(232, 222, 177, 70)),
                card.getBorder()
        ));

        GridBagConstraints resC = new GridBagConstraints();
        resC.gridx = 0; resC.gridy = 0; resC.anchor = GridBagConstraints.WEST; resC.insets = new Insets(5, 2, 7, 2);

        lblConsumo = new JLabel("Consumo estimado: --");
        lblConsumo.setFont(new Font("Arial", Font.BOLD, 17));
        card.add(lblConsumo, resC);

        lblPotencia = new JLabel("Potência do sistema: --");
        lblPotencia.setFont(new Font("Arial", Font.BOLD, 17));
        resC.gridy++;
        card.add(lblPotencia, resC);

        lblModulos = new JLabel("Módulos solares: --");
        lblModulos.setFont(new Font("Arial", Font.BOLD, 17));
        resC.gridy++;
        card.add(lblModulos, resC);

        lblArea = new JLabel("Área necessária: --");
        lblArea.setFont(new Font("Arial", Font.BOLD, 17));
        resC.gridy++;
        card.add(lblArea, resC);

        lblCusto = new JLabel("Investimento: --");
        lblCusto.setFont(new Font("Arial", Font.BOLD, 17));
        resC.gridy++;
        card.add(lblCusto, resC);

        lblEconomia = new JLabel("Economia anual: --");
        lblEconomia.setFont(new Font("Arial", Font.BOLD, 17));
        lblEconomia.setForeground(new Color(44, 154, 76));
        resC.gridy++;
        card.add(lblEconomia, resC);

        lblPayback = new JLabel("Payback: --");
        lblPayback.setFont(new Font("Arial", Font.BOLD, 17));
        lblPayback.setForeground(new Color(51, 102, 204));
        resC.gridy++;
        card.add(lblPayback, resC);

        return card;
    }

    private void simularEconomia() {
        String valorContaStr = campoConta.getText().trim();

        if (valorContaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o valor médio da conta!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double valorConta;
        try {
            valorConta = Double.parseDouble(valorContaStr.replace(",", "."));
            if (valorConta <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.listarPorUsuario(cpfUsuario);

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre ao menos um cliente antes de simular.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcoes = clientes.stream()
                .map(c -> c.getNome() + " - " + c.getCpf())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o cliente para simular:",
                "Selecionar Cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == null) return;

        String cpfClienteSelecionado = escolha.substring(escolha.lastIndexOf(" - ") + 3).trim();
        Cliente cliente = clienteDAO.buscarPorCpfCliente(cpfUsuario, cpfClienteSelecionado);

        if (cliente == null || cliente.getEstado().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dados do cliente incompletos. Cadastre/atualize o endereço antes de simular.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimulacaoEnergia simulacao = new SimulacaoEnergia(cliente.getEstado(), valorConta);

        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();
        simulacaoDAO.cadastrar(simulacao, cliente.getId());

        logoLabel.setIcon(logoPequeno);

        lblConsumo.setText("Consumo estimado: " + String.format("%.2f kWh/mês", simulacao.getConsumoEstimadoKwh()));
        lblPotencia.setText("Potência do sistema: " + String.format("%.2f kWp", simulacao.getPotenciaSistemaKw()));
        lblModulos.setText("Módulos solares: " + simulacao.getQuantidadeModulos());
        lblArea.setText("Área necessária: " + String.format("%.2f m²", simulacao.getAreaNecessariaM2()));
        lblCusto.setText("Investimento: R$ " + String.format("%.2f", simulacao.getCustoSistema()));
        lblEconomia.setText("Economia anual: R$ " + String.format("%.2f", simulacao.getEconomiaAnual()));
        lblPayback.setText("Payback: " + formatarPayback(simulacao.getPaybackAnos()));

        cardResultado.setVisible(true);
    }

    private String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";
        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);
        if (anosInt == 0 && meses == 0) return "Retorno de investimento imediato (menos de 1 mês)";
        if (anosInt == 0) return "Retorno de investimento em " + meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos");
        return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }
}
