package view;

import model.Usuario;
import database.UsuarioDAO;
import service.SessaoUsuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JButton botaoEntrar, botaoCadastrar;

    public TelaLogin() {
        setTitle("SolarDragons - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750, 700));
        setLocationRelativeTo(null);
        setResizable(true);


        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        EstiloSolarDragons.aplicarFundo(getContentPane());


        JLabel logo = EstiloSolarDragons.criarLogo(
                350, 350, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(20,0,20,0);
        add(logo, c);


        JLabel titulo = new JLabel("SolarDragons");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(0,0,25,0);
        add(titulo, c);


        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(EstiloSolarDragons.LABEL);
        cpfLabel.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 2; c.anchor = GridBagConstraints.WEST; c.insets = new Insets(0,50,5,50);
        add(cpfLabel, c);

        campoCpf = new JTextField(20);
        EstiloSolarDragons.estilizarCampo(campoCpf);
        c.gridy = 3; c.insets = new Insets(0,50,15,50);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(campoCpf, c);


        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(EstiloSolarDragons.LABEL);
        senhaLabel.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 4; c.insets = new Insets(0,50,5,50); c.fill = GridBagConstraints.NONE;
        add(senhaLabel, c);

        campoSenha = new JPasswordField(20);
        EstiloSolarDragons.estilizarCampo(campoSenha);
        c.gridy = 5; c.insets = new Insets(0,50,20,50);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(campoSenha, c);


        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        botaoEntrar = new JButton("Entrar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoEntrar);

        botaoCadastrar = new JButton("Cadastrar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoCadastrar);

        Dimension botaoSize = new Dimension(130, 40);
        botaoEntrar.setPreferredSize(botaoSize);
        botaoCadastrar.setPreferredSize(botaoSize);

        painelBotoes.add(botaoEntrar);
        painelBotoes.add(botaoCadastrar);

        c.gridy = 6; c.insets = new Insets(10,0,20,0);
        add(painelBotoes, c);

        botaoEntrar.addActionListener(e -> realizarLogin());
        botaoCadastrar.addActionListener(e -> {
            dispose();
            new TelaCadastroUsuario();
        });

        setVisible(true);
    }

    private void realizarLogin() {
        String cpf = campoCpf.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.autenticar(cpf, senha);

        if (usuario != null) {
            SessaoUsuario.login(usuario);
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

            dispose();

            if (usuario.isAdmin()) {
                new TelaADM();
            } else {
                new TelaPrincipalUsuario();
            }

        } else {
            JOptionPane.showMessageDialog(this, "CPF ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
