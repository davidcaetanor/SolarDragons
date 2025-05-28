package view;

import model.Cliente;
import model.Usuario;
import model.SimulacaoEnergia;
import database.UsuarioDAO;
import database.ClienteDAO;
import database.SimulacaoEnergiaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaADMSimulacoes extends JFrame {
    private JTable tabelaSimulacoes;
    private DefaultTableModel tableModel;

    public TelaADMSimulacoes() {
        setTitle("Simulações Registradas");
        setSize(950, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Todas as simulações realizadas:");
        label.setBounds(20, 15, 300, 25);
        add(label);

        String[] colunas = {"Usuário", "Cliente", "Conta (R$)", "Estado", "Potência (kWp)", "Módulos", "Custo (R$)", "Economia (5 anos)", "Payback"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaSimulacoes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaSimulacoes);
        scrollPane.setBounds(20, 45, 880, 240);
        add(scrollPane);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(400, 310, 100, 30);
        add(botaoVoltar);

        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaADM();
        });

        atualizarTabela();

        setVisible(true);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();

        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = clienteDAO.listarPorUsuario(usuario.getCpf());
            for (Cliente c : clientes) {
                List<SimulacaoEnergia> simulacoes = simulacaoDAO.listarPorCliente(c.getId());
                for (SimulacaoEnergia s : simulacoes) {
                    tableModel.addRow(new Object[]{
                            usuario.getNome(),
                            c.getNome(),
                            String.format("%.2f", s.getValorContaReais()),
                            c.getEstado(),
                            String.format("%.2f", s.getPotenciaSistemaKw()),
                            s.getQuantidadeModulos(),
                            String.format("R$ %.2f", s.getCustoSistema()),
                            String.format("R$ %.2f", s.getEconomiaAnual() * 5),
                            formatarPayback(s.getPaybackAnos())
                    });
                }
            }
        }
    }

    private String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";
        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);
        if (anosInt == 0 && meses == 0) return "Menos de 1 mês";
        if (anosInt == 0) return meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return anosInt + (anosInt == 1 ? " ano" : " anos");
        return anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }
}
