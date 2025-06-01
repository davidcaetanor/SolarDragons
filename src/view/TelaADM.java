package view;

import service.SessaoUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaADM extends JFrame {

    public TelaADM() {
        setTitle("Painel Administrativo - SolarDragons");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridwidth = 1;
        c.insets = new Insets(14, 0, 14, 0);

        JLabel logo = EstiloSolarDragons.criarLogo(
                200, 200, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridy = 0;
        add(logo, c);

        JLabel saudacao = new JLabel("Bem-vindo, ADM: " + SessaoUsuario.getUsuarioLogado().getNome());
        saudacao.setFont(EstiloSolarDragons.TITULO);
        saudacao.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        add(saudacao, c);

        JPanel painelBotoes = new JPanel(new GridLayout(7, 1, 0, 20));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoUsuarios = new JButton("Gerenciar Usuários");
        JButton botaoClientes = new JButton("Gerenciar Clientes");
        JButton botaoSimulacoes = new JButton("Gerenciar Simulações");
        JButton botaoParametros = new JButton("Parâmetros do Sistema");
        JButton botaoExportar = new JButton("Exportar Relatórios");
        JButton botaoEstatisticas = new JButton("Ver Estatísticas Globais");
        JButton botaoSair = new JButton("Sair");

        EstiloSolarDragons.estilizarBotaoPrincipal(botaoUsuarios);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoClientes);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoSimulacoes);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoParametros);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoExportar);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoEstatisticas);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoSair);

        Dimension btnSize = new Dimension(240, 40);
        botaoUsuarios.setPreferredSize(btnSize);
        botaoClientes.setPreferredSize(btnSize);
        botaoSimulacoes.setPreferredSize(btnSize);
        botaoParametros.setPreferredSize(btnSize);
        botaoExportar.setPreferredSize(btnSize);
        botaoEstatisticas.setPreferredSize(btnSize);
        botaoSair.setPreferredSize(btnSize);

        painelBotoes.add(botaoUsuarios);
        painelBotoes.add(botaoClientes);
        painelBotoes.add(botaoSimulacoes);
        painelBotoes.add(botaoParametros);
        painelBotoes.add(botaoExportar);
        painelBotoes.add(botaoEstatisticas);
        painelBotoes.add(botaoSair);

        c.gridy = 2; c.insets = new Insets(32,0,0,0);
        add(painelBotoes, c);

        botaoUsuarios.addActionListener(e -> {
            dispose();
            new TelaGerenciarUsuarios();
        });

        botaoClientes.addActionListener(e -> {
            dispose();
            new TelaADMClientes();
        });

        botaoSimulacoes.addActionListener(e -> {
            dispose();
            new TelaADMSimulacoes();
        });

        botaoParametros.addActionListener(e -> {
            new TelaParametrosSistema();
        });

        botaoExportar.addActionListener(e -> {
            new TelaExportarRelatorios();
        });

        botaoEstatisticas.addActionListener(e -> {
            new TelaEstatisticasGerais();
        });

        botaoSair.addActionListener(e -> {
            SessaoUsuario.logout();
            dispose();
            new TelaLogin();
        });

        setVisible(true);
    }
}
