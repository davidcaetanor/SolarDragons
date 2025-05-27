package view;

import model.Cliente;
import service.ServicoCadastroCliente;
import service.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaGerenciarClientes extends JFrame {
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;

    public TelaGerenciarClientes() {
        setTitle("Gerenciar Clientes");
        setSize(650, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelTitulo = new JLabel("Clientes cadastrados:");
        labelTitulo.setBounds(20, 15, 200, 25);
        add(labelTitulo);

        String[] colunas = {"Nome", "CPF", "Email", "Cidade", "Estado"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBounds(20, 45, 600, 180);
        add(scrollPane);

        JButton botaoAdicionar = new JButton("Adicionar Novo Cliente");
        botaoAdicionar.setBounds(20, 240, 180, 30);
        add(botaoAdicionar);

        JButton botaoEditar = new JButton("Editar Cliente");
        botaoEditar.setBounds(210, 240, 120, 30);
        add(botaoEditar);

        JButton botaoExcluir = new JButton("Excluir Cliente");
        botaoExcluir.setBounds(340, 240, 120, 30);
        add(botaoExcluir);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(470, 240, 120, 30);
        add(botaoVoltar);

        botaoAdicionar.addActionListener(e -> {
            dispose();
            new TelaCadastroCliente();
        });

        botaoEditar.addActionListener(e -> editarCliente());
        botaoExcluir.addActionListener(e -> excluirCliente());
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipalUsuario();
        });

        atualizarTabela();

        setVisible(true);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        List<Cliente> clientes = ServicoCadastroCliente.listarClientesDoUsuario(cpfUsuario);
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                    c.getNome(),
                    c.getCpf(),
                    c.getEmail(),
                    c.getCidade(),
                    c.getEstado()
            });
        }
    }

    private void editarCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        dispose();
        new TelaCadastroClienteEdicao(cpfCliente);
    }

    private void excluirCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        String nomeCliente = (String) tableModel.getValueAt(linha, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o cliente: " + nomeCliente + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
            ServicoCadastroCliente.removerCliente(cpfUsuario, cpfCliente);
            atualizarTabela();
        }
    }
}
