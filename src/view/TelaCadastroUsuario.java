package view;

import model.Usuario;
import service.AutenticacaoUser;

import javax.swing.*;

public class TelaCadastroUsuario extends JFrame {

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmarSenha;
    private JButton botaoCadastrar;
    private JButton botaoCancelar;

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(380, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(40, 30, 80, 25);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(130, 30, 180, 25);
        add(campoNome);

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(40, 70, 80, 25);
        add(labelCpf);

        campoCpf = new JTextField();
        campoCpf.setBounds(130, 70, 180, 25);
        add(campoCpf);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(40, 110, 80, 25);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(130, 110, 180, 25);
        add(campoEmail);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(40, 150, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(130, 150, 180, 25);
        add(campoSenha);

        JLabel labelConfirmarSenha = new JLabel("Confirmar Senha:");
        labelConfirmarSenha.setBounds(10, 190, 120, 25);
        add(labelConfirmarSenha);

        campoConfirmarSenha = new JPasswordField();
        campoConfirmarSenha.setBounds(130, 190, 180, 25);
        add(campoConfirmarSenha);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(80, 240, 100, 30);
        add(botaoCadastrar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(200, 240, 100, 30);
        add(botaoCancelar);

        botaoCadastrar.addActionListener(e -> cadastrarUsuario());
        botaoCancelar.addActionListener(e -> {
            dispose();
            new TelaLogin();
        });

        setVisible(true);
    }

    private void cadastrarUsuario() {
        String nome = campoNome.getText().trim();
        String cpf = campoCpf.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "CPF deve conter exatamente 11 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Informe um email válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não conferem.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Usuario novoUsuario = new Usuario(cpf, nome, email, senha, false, false);
        boolean cadastrado = AutenticacaoUser.cadastrarUsuario(novoUsuario);

        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
            new TelaLogin();
        } else {
            JOptionPane.showMessageDialog(this, "CPF já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
