package view;

import model.Usuario;
import service.AutenticacaoUser;
import service.SessaoUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;

    public TelaLogin() {
        setTitle("SolarDragons - Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(40, 30, 80, 25);
        add(labelCpf);

        campoCpf = new JTextField();
        campoCpf.setBounds(120, 30, 150, 25);
        add(campoCpf);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(40, 70, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(120, 70, 150, 25);
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(60, 120, 90, 30);
        add(botaoEntrar);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(170, 120, 100, 30);
        add(botaoCadastrar);

        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = campoCpf.getText().trim();
                String senha = new String(campoSenha.getPassword()).trim();

                Usuario usuario = AutenticacaoUser.autenticar(cpf, senha);

                if (usuario != null) {
                    SessaoUsuario.login(usuario);
                    JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");

                    dispose();

                    if (usuario.isAdmin()) {
                        new TelaADM();
                    } else {
                        new TelaPrincipalUsuario();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "CPF ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaCadastroUsuario();
            }
        });

        setVisible(true);
    }

}
