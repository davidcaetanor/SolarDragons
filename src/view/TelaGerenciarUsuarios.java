package view;

import model.Usuario;
import database.UsuarioDAO;
import service.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaGerenciarUsuarios extends JFrame {
    private JTable tabelaUsuarios;
    private DefaultTableModel tableModel;
    private boolean admRoot;

    public TelaGerenciarUsuarios() {
        setTitle("Gerenciar Usuários");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        admRoot = SessaoUsuario.getUsuarioLogado().isRootAdmin();

        JLabel label = new JLabel("Usuários do sistema:");
        label.setBounds(20, 15, 200, 25);
        add(label);

        String[] colunas = {"Nome", "CPF", "Email", "Tipo", "ADM Raiz"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        scrollPane.setBounds(20, 45, 640, 180);
        add(scrollPane);

        JButton botaoPromover = new JButton("Promover/Demitir ADM");
        botaoPromover.setBounds(20, 240, 170, 30);
        add(botaoPromover);

        JButton botaoRemover = new JButton("Remover Usuário");
        botaoRemover.setBounds(200, 240, 150, 30);
        add(botaoRemover);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(370, 240, 100, 30);
        add(botaoVoltar);

        botaoPromover.addActionListener(e -> promoverOuDemitirADM());
        botaoRemover.addActionListener(e -> removerUsuario());
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

        // Só o ADM raiz pode promover/demitir outros ADMs
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
