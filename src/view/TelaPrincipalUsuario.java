package view;

import database.ClienteDAO;
import service.SessaoUsuario;

import javax.swing.*;

public class TelaPrincipalUsuario extends JFrame {

    private JLabel saudacao;

    public TelaPrincipalUsuario() {
        setTitle("Painel do Usuário - SolarDragons");
        setSize(400, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        saudacao = new JLabel("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
        saudacao.setBounds(60, 20, 300, 30);
        add(saudacao);

        JButton botaoMeusDados = new JButton("Meus Dados");
        botaoMeusDados.setBounds(120, 60, 150, 30);
        add(botaoMeusDados);

        JButton botaoSimular = new JButton("Simular Economia");
        botaoSimular.setBounds(120, 100, 150, 30);
        add(botaoSimular);

        JButton botaoVerDados = new JButton("Gerenciar Clientes");
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

        botaoMeusDados.addActionListener(e -> {
            new TelaMeusDados(this);
            setVisible(false);
        });

        botaoSimular.addActionListener(e -> {
            String cpf = SessaoUsuario.getUsuarioLogado().getCpf();
            ClienteDAO clienteDAO = new ClienteDAO();
            boolean existeCliente = !clienteDAO.listarPorUsuario(cpf).isEmpty();

            if (!existeCliente) {
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
            new TelaGerenciarClientes();
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

    public void atualizarSaudacao() {
        saudacao.setText("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
    }
}
