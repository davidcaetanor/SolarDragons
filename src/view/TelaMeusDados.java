package view;


import service.SessaoUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaMeusDados extends JFrame {
    private JTextField campoNome, campoEmail, campoCpf;
    private JPasswordField campoSenhaAntiga, campoSenhaNova, campoSenhaConfirma;
    private JButton botaoSalvar, botaoCancelar;
    private TelaPrincipalUsuario telaPrincipal;

    public TelaMeusDados(TelaPrincipalUsuario telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        setTitle("Meus Dados");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        EstiloSolarDragons.aplicarFundo(getContentPane());


        JLabel logo = EstiloSolarDragons.criarLogo(
                180, 180, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(20,0,10,0);
        add(logo, c);

        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setFont(EstiloSolarDragons.LABEL);
        labelCpf.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(0,50,4,50); c.anchor = GridBagConstraints.WEST;
        add(labelCpf, c);

        campoCpf = new JTextField(usuario.getCpf());
        EstiloSolarDragons.estilizarCampo(campoCpf);
        campoCpf.setEditable(false);
        c.gridy = 2; c.insets = new Insets(0,50,10,50); c.fill = GridBagConstraints.HORIZONTAL;
        add(campoCpf, c);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(EstiloSolarDragons.LABEL);
        labelNome.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 3; c.insets = new Insets(0,50,4,50);
        add(labelNome, c);
        campoNome = new JTextField(usuario.getNome());
        EstiloSolarDragons.estilizarCampo(campoNome);
        c.gridy = 4; c.insets = new Insets(0,50,10,50);
        add(campoNome, c);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(EstiloSolarDragons.LABEL);
        labelEmail.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 5; c.insets = new Insets(0,50,4,50);
        add(labelEmail, c);
        campoEmail = new JTextField(usuario.getEmail());
        EstiloSolarDragons.estilizarCampo(campoEmail);
        c.gridy = 6; c.insets = new Insets(0,50,10,50);
        add(campoEmail, c);

        JLabel labelSenhaAntiga = new JLabel("Senha Atual:");
        labelSenhaAntiga.setFont(EstiloSolarDragons.LABEL);
        labelSenhaAntiga.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 7; c.insets = new Insets(0,50,4,50);
        add(labelSenhaAntiga, c);
        campoSenhaAntiga = new JPasswordField();
        EstiloSolarDragons.estilizarCampo(campoSenhaAntiga);
        c.gridy = 8; c.insets = new Insets(0,50,10,50);
        add(campoSenhaAntiga, c);

        JLabel labelSenhaNova = new JLabel("Nova Senha:");
        labelSenhaNova.setFont(EstiloSolarDragons.LABEL);
        labelSenhaNova.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 9; c.insets = new Insets(0,50,4,50);
        add(labelSenhaNova, c);
        campoSenhaNova = new JPasswordField();
        EstiloSolarDragons.estilizarCampo(campoSenhaNova);
        c.gridy = 10; c.insets = new Insets(0,50,10,50);
        add(campoSenhaNova, c);

        JLabel labelSenhaConfirma = new JLabel("Confirmar Nova:");
        labelSenhaConfirma.setFont(EstiloSolarDragons.LABEL);
        labelSenhaConfirma.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 11; c.insets = new Insets(0,50,4,50);
        add(labelSenhaConfirma, c);
        campoSenhaConfirma = new JPasswordField();
        EstiloSolarDragons.estilizarCampo(campoSenhaConfirma);
        c.gridy = 12; c.insets = new Insets(0,50,16,50);
        add(campoSenhaConfirma, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        botaoSalvar = new JButton("Salvar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSalvar);
        botaoCancelar = new JButton("Cancelar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoCancelar);
        Dimension botaoSize = new Dimension(120, 36);
        botaoSalvar.setPreferredSize(botaoSize);
        botaoCancelar.setPreferredSize(botaoSize);

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        c.gridy = 13; c.insets = new Insets(10,0,20,0);
        add(painelBotoes, c);

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
