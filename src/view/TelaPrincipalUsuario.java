package view;

import service.SessaoUsuario;
import service.ServicoCadastroCliente;

import javax.swing.*;

public class TelaPrincipalUsuario extends JFrame {

    public TelaPrincipalUsuario() {
        setTitle("Painel do Usuário - SolarDragons");
        setSize(400, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel saudacao = new JLabel("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
        saudacao.setBounds(60, 20, 300, 30);
        add(saudacao);

        JButton botaoGerenciarClientes = new JButton("Gerenciar Clientes");
        botaoGerenciarClientes.setBounds(120, 60, 150, 30);
        add(botaoGerenciarClientes);

        JButton botaoSimular = new JButton("Simular Economia");
        botaoSimular.setBounds(120, 100, 150, 30);
        add(botaoSimular);

        JButton botaoVerDados = new JButton("Visualizar Dados");
        botaoVerDados.setBounds(120, 140, 150, 30);
        add(botaoVerDados);

        JButton botaoGrafico = new JButton("Ver Gráfico");
        botaoGrafico.setBounds(120, 180, 150, 30);
        add(botaoGrafico);

        JButton botaoExportar = new JButton("Exportar Relatório");
        botaoExportar.setBounds(120, 220, 150, 30);
        add(botaoExportar);

        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(120, 260, 150, 30);
        add(botaoSair);

        botaoGerenciarClientes.addActionListener(e -> {
            dispose();
            new TelaGerenciarClientes();
        });

        botaoSimular.addActionListener(e -> {
            String cpf = SessaoUsuario.getUsuarioLogado().getCpf();
            if (!ServicoCadastroCliente.clienteExiste(cpf)) {
                JOptionPane.showMessageDialog(this, "Você precisa cadastrar seus dados antes de simular!", "Atenção", JOptionPane.WARNING_MESSAGE);
                dispose();
                new TelaCadastroCliente();
            } else {
                dispose();
                new TelaSimulacaoEconomia();
            }
        });

        botaoVerDados.addActionListener(e -> {
            dispose();
            new TelaCadastroCliente();
        });

        botaoGrafico.addActionListener(e -> {
            GraficoEconomia.exibirGraficoEconomia();
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
}
