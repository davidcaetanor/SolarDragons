package view;


import service.SessaoUsuario;
import database.ClienteDAO;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipalUsuario extends JFrame {

    private JLabel saudacao;

    public TelaPrincipalUsuario() {
        setTitle("Painel do Usuário - SolarDragons");
        setMinimumSize(new Dimension(750, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        EstiloSolarDragons.aplicarFundo(getContentPane());


        JLabel logo = EstiloSolarDragons.criarLogo(200, 200, "/resources/iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(18,0,6,0);
        add(logo, c);

        saudacao = new JLabel("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
        saudacao.setFont(EstiloSolarDragons.TITULO);
        saudacao.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(4,0,24,0);
        add(saudacao, c);

        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 0, 18));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoMeusDados = new JButton("Meus Dados");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoMeusDados);
        painelBotoes.add(botaoMeusDados);

        JButton botaoSimular = new JButton("Simular Economia");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSimular);
        painelBotoes.add(botaoSimular);

        JButton botaoVerDados = new JButton("Gerenciar Clientes");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoVerDados);
        painelBotoes.add(botaoVerDados);

        JButton botaoGrafico = new JButton("Ver Gráfico");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoGrafico);
        painelBotoes.add(botaoGrafico);

        JButton botaoExportar = new JButton("Exportar Relatório");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoExportar);
        // painelBotoes.add(botaoExportar); desabilitado

        JButton botaoSair = new JButton("Sair");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSair);
        painelBotoes.add(botaoSair);

        Dimension botaoSize = new Dimension(200, 40);
        for (Component comp : painelBotoes.getComponents()) {
            if (comp instanceof JButton) {
                ((JButton) comp).setPreferredSize(botaoSize);
            }
        }

        c.gridy = 2; c.insets = new Insets(4,0,20,0); c.fill = GridBagConstraints.HORIZONTAL;
        add(painelBotoes, c);

        botaoMeusDados.addActionListener(e -> {
            new TelaMeusDados(this);
            setVisible(false);
        });
        botaoSimular.addActionListener(e -> {
            String cpf = SessaoUsuario.getUsuarioLogado().getCpf();
            ClienteDAO clienteDAO = new ClienteDAO();
            boolean existeCliente = !clienteDAO.listarPorUsuario(cpf).isEmpty();

            if (!existeCliente) {
                JOptionPane.showMessageDialog(this, "Voce precisa cadastrar ao menos um cliente antes de simular!", "Atenção", JOptionPane.WARNING_MESSAGE);
                dispose();
                new TelaCadastroCliente();
            } else {
                dispose();
                new TelaSimulacaoEconomia();
            }
        });
        botaoVerDados.addActionListener(e -> {
            dispose();
            new TelaGerenciarClientes();
        });
        botaoGrafico.addActionListener(e -> {
            new GraficoEconomia();
        });
        botaoExportar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Função em desenvolvimento! Exportação será implementada em breve.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        botaoSair.addActionListener(e -> {
            SessaoUsuario.logout();
            dispose();
            new TelaLogin();
        });

        setVisible(true);
    }

    public void atualizarSaudacao() {
        saudacao.setText("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
    }
}
