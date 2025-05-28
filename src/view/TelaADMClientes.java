package view;

import model.Cliente;
import model.Usuario;
import database.UsuarioDAO;
import database.ClienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaADMClientes extends JFrame {
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;

    public TelaADMClientes() {
        setTitle("Clientes do Sistema");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Todos os clientes cadastrados:");
        label.setBounds(20, 15, 300, 25);
        add(label);

        String[] colunas = {"Nome Cliente", "CPF Cliente", "Email", "Cidade", "Estado", "Usuário Responsável"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBounds(20, 45, 740, 240);
        add(scrollPane);

        JButton botaoRemover = new JButton("Remover Cliente");
        botaoRemover.setBounds(20, 300, 150, 30);
        add(botaoRemover);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(200, 300, 100, 30);
        add(botaoVoltar);

        botaoRemover.addActionListener(e -> removerCliente());
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
        List<Usuario> usuarios = usuarioDAO.listar();

        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = clienteDAO.listarPorUsuario(usuario.getCpf());
            for (Cliente c : clientes) {
                tableModel.addRow(new Object[]{
                        c.getNome(), c.getCpf(), c.getEmail(), c.getCidade(), c.getEstado(), usuario.getNome()
                });
            }
        }
    }

    private void removerCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        String usuarioNome = (String) tableModel.getValueAt(linha, 5);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(usuarioNome)) {
                clienteDAO.remover(usuario.getCpf(), cpfCliente);
                break;
            }
        }
        atualizarTabela();
    }
}
