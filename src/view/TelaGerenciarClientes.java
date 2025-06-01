package view;

import model.Cliente;
import database.ClienteDAO;
import service.SessaoUsuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TelaGerenciarClientes extends JFrame {
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;
    private JTextField campoBusca;
    private JButton botaoOrdenarAZ, botaoBuscar;
    private boolean ordemAscendente = true;
    private List<Cliente> clientesDoUsuario;
    private final String placeholder = "Digite o nome aqui";

    public TelaGerenciarClientes() {
        setTitle("Gerenciar Clientes - SolarDragons");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());

        JLabel titulo = new JLabel("Gerenciar Clientes", JLabel.CENTER);
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout());
        EstiloSolarDragons.aplicarFundo(painelCentral);

        JPanel painelBusca = new JPanel(new BorderLayout());
        EstiloSolarDragons.aplicarFundo(painelBusca);

        campoBusca = new JTextField();
        EstiloSolarDragons.estilizarCampo(campoBusca);
        campoBusca.setText(placeholder);
        campoBusca.setForeground(Color.GRAY);
        campoBusca.setFont(campoBusca.getFont().deriveFont(Font.ITALIC));
        painelBusca.add(campoBusca, BorderLayout.CENTER);

        campoBusca.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (campoBusca.getText().equals(placeholder)) {
                    campoBusca.setText("");
                    campoBusca.setForeground(EstiloSolarDragons.AZUL_ESCURO);
                    campoBusca.setFont(campoBusca.getFont().deriveFont(Font.PLAIN));
                }
            }
            public void focusLost(FocusEvent e) {
                if (campoBusca.getText().trim().isEmpty()) {
                    campoBusca.setForeground(Color.GRAY);
                    campoBusca.setFont(campoBusca.getFont().deriveFont(Font.ITALIC));
                    campoBusca.setText(placeholder);
                }
            }
        });

        botaoBuscar = new JButton("Buscar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoBuscar);
        botaoBuscar.setPreferredSize(new Dimension(120, 35));
        painelBusca.add(botaoBuscar, BorderLayout.EAST);

        painelCentral.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Nome", "CPF", "Email", "Cidade", "Estado"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientes = new JTable(tableModel);

        tabelaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                } else {
                    c.setBackground(new Color(220, 230, 245));
                }
                c.setForeground(EstiloSolarDragons.AZUL_ESCURO);
                return c;
            }
        });
        tabelaClientes.setRowHeight(28);

        JTableHeader header = tabelaClientes.getTableHeader();
        header.setBackground(EstiloSolarDragons.AZUL_ESCURO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        painelCentral.add(scrollPane, BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoExcluir = new JButton("Excluir");
        botaoOrdenarAZ = new JButton("Ordenar A-Z");
        JButton botaoVoltar = new JButton("Voltar");

        EstiloSolarDragons.estilizarBotaoPrincipal(botaoAdicionar);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoEditar);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoExcluir);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoOrdenarAZ);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoVoltar);

        Dimension tamanhoBotoes = new Dimension(120, 35);
        botaoAdicionar.setPreferredSize(tamanhoBotoes);
        botaoEditar.setPreferredSize(tamanhoBotoes);
        botaoExcluir.setPreferredSize(tamanhoBotoes);
        botaoOrdenarAZ.setPreferredSize(tamanhoBotoes);
        botaoVoltar.setPreferredSize(tamanhoBotoes);

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoOrdenarAZ);
        painelBotoes.add(botaoVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        botaoAdicionar.addActionListener(e -> {
            dispose();
            new TelaCadastroCliente();
        });

        botaoEditar.addActionListener(e -> editarCliente());
        botaoExcluir.addActionListener(e -> excluirCliente());
        botaoVoltar.addActionListener(e -> {
            TelaUtil.voltarParaPainelUsuario(this);
        });

        botaoBuscar.addActionListener(e -> filtrarTabela());
        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrarTabela(); }
            public void removeUpdate(DocumentEvent e) { filtrarTabela(); }
            public void changedUpdate(DocumentEvent e) { filtrarTabela(); }
        });

        botaoOrdenarAZ.addActionListener(e -> {
            ordemAscendente = !ordemAscendente;
            botaoOrdenarAZ.setText(ordemAscendente ? "Ordenar A-Z" : "Ordenar Z-A");
            ordenarTabela();
        });

        carregarClientes();
        atualizarTabela(clientesDoUsuario);

        setVisible(true);
        this.getRootPane().requestFocusInWindow();
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
        if (filtro.equals(placeholder.toLowerCase())) filtro = "";
        String finalFiltro = filtro;
        List<Cliente> filtrados = clientesDoUsuario.stream()
                .filter(c -> c.getNome().toLowerCase().contains(finalFiltro) || c.getCpf().contains(finalFiltro))
                .collect(Collectors.toList());
        atualizarTabela(filtrados);
    }

    private void ordenarTabela() {
        Comparator<Cliente> comparador = Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER);
        clientesDoUsuario = ordemAscendente ? clientesDoUsuario.stream().sorted(comparador).collect(Collectors.toList())
                : clientesDoUsuario.stream().sorted(comparador.reversed()).collect(Collectors.toList());
        filtrarTabela();
    }

    private void editarCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        boolean adminEditando = SessaoUsuario.getUsuarioLogado().isAdmin();
        dispose();
        new TelaCadastroClienteEdicao(cpfCliente, cpfUsuario, adminEditando);
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
