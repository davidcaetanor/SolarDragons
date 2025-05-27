package view;

import model.Usuario;
import service.SessaoUsuario;

import javax.swing.*;

public class TelaMeusDados extends JFrame {
    private JTextField campoNome, campoEmail, campoCpf;
    private JPasswordField campoSenhaAntiga, campoSenhaNova, campoSenhaConfirma;
    private JButton botaoSalvar, botaoCancelar;
    private TelaPrincipalUsuario telaPrincipal;

    public TelaMeusDados(TelaPrincipalUsuario telaPrincipal) {
        this.telaPrincipal = telaPrincipal;

        setTitle("Meus Dados");
        setSize(400, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(40, 30, 80, 25);
        add(labelCpf);
        campoCpf = new JTextField(usuario.getCpf());
        campoCpf.setBounds(130, 30, 200, 25);
        campoCpf.setEditable(false);
        add(campoCpf);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(40, 70, 80, 25);
        add(labelNome);
        campoNome = new JTextField(usuario.getNome());
        campoNome.setBounds(130, 70, 200, 25);
        add(campoNome);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(40, 110, 80, 25);
        add(labelEmail);
        campoEmail = new JTextField(usuario.getEmail());
        campoEmail.setBounds(130, 110, 200, 25);
        add(campoEmail);

        JLabel labelSenhaAntiga = new JLabel("Senha Atual:");
        labelSenhaAntiga.setBounds(30, 150, 100, 25);
        add(labelSenhaAntiga);
        campoSenhaAntiga = new JPasswordField();
        campoSenhaAntiga.setBounds(130, 150, 200, 25);
        add(campoSenhaAntiga);

        JLabel labelSenhaNova = new JLabel("Nova Senha:");
        labelSenhaNova.setBounds(30, 190, 100, 25);
        add(labelSenhaNova);
        campoSenhaNova = new JPasswordField();
        campoSenhaNova.setBounds(130, 190, 200, 25);
        add(campoSenhaNova);

        JLabel labelSenhaConfirma = new JLabel("Confirmar Nova:");
        labelSenhaConfirma.setBounds(10, 230, 120, 25);
        add(labelSenhaConfirma);
        campoSenhaConfirma = new JPasswordField();
        campoSenhaConfirma.setBounds(130, 230, 200, 25);
        add(campoSenhaConfirma);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(70, 280, 100, 30);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(200, 280, 100, 30);
        add(botaoCancelar);

        botaoSalvar.addActionListener(e -> salvar());
        botaoCancelar.addActionListener(e -> cancelar());

        setVisible(true);
    }

    private void salvar() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senhaAntiga = new String(campoSenhaAntiga.getPassword());
        String senhaNova = new String(campoSenhaNova.getPassword());
        String senhaConfirma = new String(campoSenhaConfirma.getPassword());

        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e email não podem ser vazios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Email inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!senhaNova.isEmpty() || !senhaConfirma.isEmpty()) {
            if (!senhaAntiga.equals(usuario.getSenha())) {
                JOptionPane.showMessageDialog(this, "Senha atual incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!senhaNova.equals(senhaConfirma)) {
                JOptionPane.showMessageDialog(this, "Nova senha e confirmação não conferem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuario.setSenha(senhaNova);
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
        dispose();

        telaPrincipal.atualizarSaudacao();
        telaPrincipal.setVisible(true);
    }

    private void cancelar() {
        dispose();
        telaPrincipal.setVisible(true);
    }
}
