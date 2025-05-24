package view;

import service.SessaoUsuario;

import javax.swing.*;

public class TelaADM extends JFrame {

    public TelaADM() {
        setTitle("Painel Administrativo - SolarDragons");
        setSize(500, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel saudacao = new JLabel("Bem-vindo, ADM: " + SessaoUsuario.getUsuarioLogado().getNome());
        saudacao.setBounds(60, 20, 380, 30);
        saudacao.setFont(saudacao.getFont().deriveFont(16f));
        add(saudacao);

        JButton botaoUsuarios = new JButton("Gerenciar Usuários");
        botaoUsuarios.setBounds(140, 70, 200, 35);
        add(botaoUsuarios);

        JButton botaoClientes = new JButton("Gerenciar Clientes");
        botaoClientes.setBounds(140, 120, 200, 35);
        add(botaoClientes);

        JButton botaoSimulacoes = new JButton("Gerenciar Simulações");
        botaoSimulacoes.setBounds(140, 170, 200, 35);
        add(botaoSimulacoes);

        JButton botaoParametros = new JButton("Parâmetros do Sistema");
        botaoParametros.setBounds(140, 220, 200, 35);
        add(botaoParametros);

        JButton botaoExportar = new JButton("Exportar Relatórios");
        botaoExportar.setBounds(140, 270, 200, 35);
        add(botaoExportar);

        JButton botaoEstatisticas = new JButton("Ver Estatísticas Globais");
        botaoEstatisticas.setBounds(140, 320, 200, 35);
        add(botaoEstatisticas);

        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(140, 370, 200, 35);
        add(botaoSair);

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
            new TelaEstatisticasGlobais();
        });

        botaoSair.addActionListener(e -> {
            SessaoUsuario.logout();
            dispose();
            new TelaLogin();
        });

        setVisible(true);
    }
}
