package view;

import model.Cliente;
import model.Usuario;
import database.UsuarioDAO;
import database.ClienteDAO;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TelaADMClientes extends JFrame {
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;
    private JTextField campoBusca;
    private JButton botaoEditar, botaoRemover, botaoVoltar;
    private List<Cliente> todosClientes;
    private List<Usuario> usuarios;

    public TelaADMClientes() {
        setTitle("Clientes do Sistema");
        setMinimumSize(new Dimension(850, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(18, 0, 12, 0); c.gridx = 0; c.gridwidth = 2;

        JLabel logo = EstiloSolarDragons.criarLogo(140, 140, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridy = 0; c.anchor = GridBagConstraints.CENTER;
        add(logo, c);

        JLabel titulo = new JLabel("Todos os clientes cadastrados");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        add(titulo, c);

        JPanel painelBusca = new JPanel(new BorderLayout());
        EstiloSolarDragons.aplicarFundo(painelBusca);
        campoBusca = new JTextField();
        EstiloSolarDragons.estilizarCampo(campoBusca);
        campoBusca.setPreferredSize(new Dimension(300, 30));
        painelBusca.add(campoBusca, BorderLayout.CENTER);

        JLabel labelBusca = new JLabel("Buscar:");
        EstiloSolarDragons.estilizarLabel(labelBusca);
        painelBusca.add(labelBusca, BorderLayout.WEST);

        c.gridy = 2; c.insets = new Insets(5, 0, 7, 0); c.fill = GridBagConstraints.NONE;
        add(painelBusca, c);

        String[] colunas = {"Nome Cliente", "CPF Cliente", "Email", "Cidade", "Estado", "Usuário Responsável"};
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
        scrollPane.setPreferredSize(new Dimension(820, 340));

        c.gridy = 3; c.insets = new Insets(6, 0, 14, 0); c.fill = GridBagConstraints.BOTH;
        add(scrollPane, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        botaoEditar = new JButton("Editar Cliente");
        botaoRemover = new JButton("Remover Cliente");
        botaoVoltar = new JButton("Voltar");
        Dimension btnSize = new Dimension(160, 38);

        EstiloSolarDragons.estilizarBotaoPrincipal(botaoEditar);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoRemover);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoVoltar);

        botaoEditar.setPreferredSize(btnSize);
        botaoRemover.setPreferredSize(btnSize);
        botaoVoltar.setPreferredSize(btnSize);

        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoVoltar);

        c.gridy = 4; c.insets = new Insets(12, 0, 12, 0); c.fill = GridBagConstraints.NONE;
        add(painelBotoes, c);

        botaoEditar.addActionListener(e -> editarCliente());
        botaoRemover.addActionListener(e -> removerCliente());
        botaoVoltar.addActionListener(e -> {
            TelaUtil.voltarParaPainelUsuario(this);
        });


        campoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(); }
        });

        carregarDados();
        atualizarTabela(todosClientes);

        setVisible(true);
    }

    private void carregarDados() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        usuarios = usuarioDAO.listar();
        todosClientes = usuarios.stream()
                .flatMap(u -> clienteDAO.listarPorUsuario(u.getCpf()).stream())
                .collect(Collectors.toList());
    }

    private void atualizarTabela(List<Cliente> lista) {
        tableModel.setRowCount(0);
        for (Cliente c : lista) {
            String usuarioResp = usuarios.stream()
                    .filter(u -> u.getCpf().equals(c.getCpfUsuario()))
                    .map(Usuario::getNome)
                    .findFirst()
                    .orElse(c.getCpfUsuario().equals("777") ? "ADM RAIZ" : "Desconhecido");
            tableModel.addRow(new Object[]{
                    c.getNome(), c.getCpf(), c.getEmail(), c.getCidade(), c.getEstado(), usuarioResp
            });
        }
    }

    private void filtrarTabela() {
        String texto = campoBusca.getText().trim().toLowerCase();
        List<Cliente> filtrados = todosClientes.stream()
                .filter(c ->
                        c.getNome().toLowerCase().contains(texto) ||
                                c.getCpf().contains(texto) ||
                                c.getEmail().toLowerCase().contains(texto) ||
                                c.getCidade().toLowerCase().contains(texto))
                .collect(Collectors.toList());
        atualizarTabela(filtrados);
    }

    private void editarCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        String usuarioResp = (String) tableModel.getValueAt(linha, 5);
        String cpfUsuario = usuarios.stream()
                .filter(u -> u.getNome().equals(usuarioResp) || (usuarioResp.equals("ADM RAIZ") && u.getCpf().equals("777")))
                .map(Usuario::getCpf)
                .findFirst()
                .orElse("777");
        dispose();
        new TelaCadastroClienteEdicao(cpfCliente, cpfUsuario, true);
    }

    private void removerCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpfCliente = (String) tableModel.getValueAt(linha, 1);
        String usuarioResp = (String) tableModel.getValueAt(linha, 5);

        String cpfUsuario = usuarios.stream()
                .filter(u -> u.getNome().equals(usuarioResp) || (usuarioResp.equals("ADM RAIZ") && u.getCpf().equals("777")))
                .map(Usuario::getCpf)
                .findFirst()
                .orElse("777");

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.remover(cpfUsuario, cpfCliente);
        carregarDados();
        filtrarTabela();
    }
}
