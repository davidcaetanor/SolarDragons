package view;

import database.UsuarioDAO;
import model.Usuario;
import model.ValidadorCPF;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {

    private JTextField campoNome, campoCpf, campoEmail;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JButton botaoCadastrar, botaoCancelar;

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário - SolarDragons");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        EstiloSolarDragons.aplicarFundo(getContentPane());

        JLabel logo = EstiloSolarDragons.criarLogo(
                250, 250, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 15, 0);
        add(logo, c);

        JLabel titulo = new JLabel("Cadastro de Usuário");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        c.insets = new Insets(0, 0, 20, 0);
        add(titulo, c);

        c.anchor = GridBagConstraints.WEST;

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(EstiloSolarDragons.LABEL);
        labelNome.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 2;
        c.insets = new Insets(5, 50, 5, 50);
        add(labelNome, c);

        campoNome = new JTextField(20);
        EstiloSolarDragons.estilizarCampo(campoNome);
        c.gridy = 3;
        c.insets = new Insets(5, 50, 10, 50);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(campoNome, c);

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setFont(EstiloSolarDragons.LABEL);
        labelCpf.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 4;
        c.insets = new Insets(5, 50, 5, 50);
        add(labelCpf, c);

        campoCpf = new JTextField(20);
        EstiloSolarDragons.estilizarCampo(campoCpf);
        c.gridy = 5;
        c.insets = new Insets(5, 50, 10, 50);
        add(campoCpf, c);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(EstiloSolarDragons.LABEL);
        labelEmail.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 6;
        c.insets = new Insets(5, 50, 5, 50);
        add(labelEmail, c);

        campoEmail = new JTextField(20);
        EstiloSolarDragons.estilizarCampo(campoEmail);
        c.gridy = 7;
        c.insets = new Insets(5, 50, 10, 50);
        add(campoEmail, c);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(EstiloSolarDragons.LABEL);
        labelSenha.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 8;
        c.insets = new Insets(5, 50, 5, 50);
        add(labelSenha, c);

        campoSenha = new JPasswordField(20);
        EstiloSolarDragons.estilizarCampo(campoSenha);
        c.gridy = 9;
        c.insets = new Insets(5, 50, 10, 50);
        add(campoSenha, c);

        JLabel labelConfirmaSenha = new JLabel("Confirmar Senha:");
        labelConfirmaSenha.setFont(EstiloSolarDragons.LABEL);
        labelConfirmaSenha.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 10;
        c.insets = new Insets(5, 50, 5, 50);
        add(labelConfirmaSenha, c);

        campoConfirmarSenha = new JPasswordField(20);
        EstiloSolarDragons.estilizarCampo(campoConfirmarSenha);
        c.gridy = 11;
        c.insets = new Insets(5, 50, 20, 50);
        add(campoConfirmarSenha, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        botaoCadastrar = new JButton("Cadastrar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoCadastrar);

        botaoCancelar = new JButton("Cancelar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoCancelar);

        Dimension botaoSize = new Dimension(140, 40);
        botaoCadastrar.setPreferredSize(botaoSize);
        botaoCancelar.setPreferredSize(botaoSize);

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoCancelar);

        c.gridy = 12;
        c.insets = new Insets(10, 0, 20, 0);
        add(painelBotoes, c);

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

        if (!ValidadorCPF.isCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido! Digite um CPF real e válido.", "Erro", JOptionPane.ERROR_MESSAGE);
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

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.cpfExiste(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.emailExiste(email)) {
            JOptionPane.showMessageDialog(this, "Este e-mail já está em uso!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario(cpf, nome, email, senha, false, false);
        boolean cadastrado = usuarioDAO.cadastrar(novoUsuario);

        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
            new TelaLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao cadastrar o usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
