package view;

import model.Cliente;
import database.ClienteDAO;
import service.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class TelaGerenciarClientes extends JFrame {
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;
    private JTextField campoBusca;
    private JButton botaoOrdenarAZ;
    private boolean ordemAscendente = true;

    private List<Cliente> clientesDoUsuario; // Para filtros e ordenação

    public TelaGerenciarClientes() {
        setTitle("Gerenciar Clientes");
        setSize(700, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelTitulo = new JLabel("Clientes cadastrados:");
        labelTitulo.setBounds(20, 15, 200, 25);
        add(labelTitulo);

        campoBusca = new JTextField();
        campoBusca.setBounds(180, 15, 220, 25);
        add(campoBusca);

        botaoOrdenarAZ = new JButton("Ordenar A-Z");
        botaoOrdenarAZ.setBounds(420, 15, 120, 25);
        add(botaoOrdenarAZ);

        String[] colunas = {"Nome", "CPF", "Email", "Cidade", "Estado"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBounds(20, 55, 640, 200);
        add(scrollPane);

        JButton botaoAdicionar = new JButton("Adicionar Novo Cliente");
        botaoAdicionar.setBounds(20, 280, 180, 30);
        add(botaoAdicionar);

        JButton botaoEditar = new JButton("Editar Cliente");
        botaoEditar.setBounds(210, 280, 120, 30);
        add(botaoEditar);

        JButton botaoExcluir = new JButton("Excluir Cliente");
        botaoExcluir.setBounds(340, 280, 120, 30);
        add(botaoExcluir);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(470, 280, 120, 30);
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


        campoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
        });


        botaoOrdenarAZ.addActionListener(e -> {
            ordemAscendente = !ordemAscendente;
            botaoOrdenarAZ.setText(ordemAscendente ? "Ordenar A-Z" : "Ordenar Z-A");
            ordenarTabela();
        });

        carregarClientes();
        atualizarTabela(clientesDoUsuario);

        setVisible(true);
    }

    private void carregarClientes() {
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        ClienteDAO clienteDAO = new ClienteDAO();
        clientesDoUsuario = clienteDAO.listarPorUsuario(cpfUsuario);
    }

    private void atualizarTabela(List<Cliente> lista) {
        tableModel.setRowCount(0);
        for (Cliente c : lista) {
            tableModel.addRow(new Object[]{
                    c.getNome(),
                    c.getCpf(),
                    c.getEmail(),
                    c.getCidade(),
                    c.getEstado()
            });
        }
    }

    private void filtrarTabela() {
        String filtro = campoBusca.getText().trim().toLowerCase();
        List<Cliente> filtrados = clientesDoUsuario.stream()
                .filter(c ->
                        c.getNome().toLowerCase().contains(filtro) ||
                                c.getCpf().contains(filtro))
                .collect(Collectors.toList());
        atualizarTabela(filtrados);
    }

    private void ordenarTabela() {
        Comparator<Cliente> comparador = Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER);
        if (!ordemAscendente) {
            comparador = comparador.reversed();
        }
        clientesDoUsuario = clientesDoUsuario.stream().sorted(comparador).collect(Collectors.toList());
        filtrarTabela();
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
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.remover(cpfUsuario, cpfCliente);
            carregarClientes();
            filtrarTabela();
        }
    }
}
