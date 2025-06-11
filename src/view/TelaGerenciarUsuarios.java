package view;

import model.Usuario;
import database.UsuarioDAO;
import service.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class TelaGerenciarUsuarios extends JFrame {
    private JTable tabelaUsuarios;
    private DefaultTableModel tableModel;
    private boolean admRoot;

    public TelaGerenciarUsuarios() {
        setTitle("Gerenciar Usuários");
        setMinimumSize(new Dimension(830, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridwidth = 2; c.insets = new Insets(24, 0, 10, 0);

        JLabel logo = EstiloSolarDragons.criarLogo(180, 180, "/resources/iconSolarDragons.png");
        c.gridy = 0;
        add(logo, c);

        JLabel titulo = new JLabel("Gerenciar Usuários");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        add(titulo, c);

        admRoot = SessaoUsuario.getUsuarioLogado().isRootAdmin();

        String[] colunas = {"Nome", "CPF", "Email", "Tipo", "ADM Raiz"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaUsuarios = new JTable(tableModel);
        tabelaUsuarios.setRowHeight(30);

        JTableHeader header = tabelaUsuarios.getTableHeader();
        header.setBackground(EstiloSolarDragons.AZUL_ESCURO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        tabelaUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        scrollPane.setPreferredSize(new Dimension(700, 260));
        c.gridy = 2; c.insets = new Insets(16, 0, 22, 0);
        add(scrollPane, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoPromover = new JButton("Promover/Demitir ADM");
        JButton botaoRemover = new JButton("Remover Usuário");
        JButton botaoVoltar = new JButton("Voltar");

        EstiloSolarDragons.estilizarBotaoPrincipal(botaoPromover);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoRemover);
        EstiloSolarDragons.estilizarBotaoSecundario(botaoVoltar);

        Dimension btnSize = new Dimension(180, 38);
        botaoPromover.setPreferredSize(btnSize);
        botaoRemover.setPreferredSize(btnSize);
        botaoVoltar.setPreferredSize(btnSize);

        painelBotoes.add(botaoPromover);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoVoltar);

        c.gridy = 3; c.insets = new Insets(10, 0, 10, 0);
        add(painelBotoes, c);

        botaoPromover.addActionListener(e -> promoverOuDemitirADM());
        botaoRemover.addActionListener(e -> removerUsuario());
        botaoVoltar.addActionListener(e -> {
            TelaUtil.voltarParaPainelUsuario(this);
        });


        atualizarTabela();
        setVisible(true);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{
                    u.getNome(), u.getCpf(), u.getEmail(),
                    u.isAdmin() ? "ADM" : "Usuário",
                    u.isRootAdmin() ? "SIM" : "NÃO"
            });
        }
    }

    private void promoverOuDemitirADM() {
        int linha = tabelaUsuarios.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpf = (String) tableModel.getValueAt(linha, 1);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.buscarPorCpf(cpf);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!admRoot && u.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Apenas o ADM raiz pode alterar privilégios de outros ADMs.", "Acesso negado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (u.isRootAdmin()) {
            JOptionPane.showMessageDialog(this, "O ADM raiz não pode ser rebaixado!", "Acesso negado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (u.isAdmin()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja remover os privilégios de ADM deste usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                u.setAdmin(false);
                usuarioDAO.atualizar(u);
                atualizarTabela();
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja promover este usuário a ADM?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                u.setAdmin(true);
                usuarioDAO.atualizar(u);
                atualizarTabela();
            }
        }
    }

    private void removerUsuario() {
        int linha = tabelaUsuarios.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpf = (String) tableModel.getValueAt(linha, 1);

        if (cpf.equals(SessaoUsuario.getUsuarioLogado().getCpf())) {
            JOptionPane.showMessageDialog(this, "Você não pode remover a si mesmo!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.buscarPorCpf(cpf);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (u.isRootAdmin()) {
            JOptionPane.showMessageDialog(this, "O ADM raiz não pode ser removido!", "Acesso negado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (u.isAdmin() && !admRoot) {
            JOptionPane.showMessageDialog(this, "Apenas o ADM raiz pode remover outros ADMs.", "Acesso negado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja remover este usuário?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            usuarioDAO.remover(cpf);
            atualizarTabela();
        }
    }
}
