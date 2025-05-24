package view;

import service.SessaoUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipalUsuario extends JFrame {

    public TelaPrincipalUsuario() {
        setTitle("Painel do Usuário - SolarDragons");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel saudacao = new JLabel("Bem-vindo, " + SessaoUsuario.getUsuarioLogado().getNome() + "!");
        saudacao.setBounds(60, 20, 300, 30);
        add(saudacao);

        JButton botaoSimular = new JButton("Simular Economia");
        botaoSimular.setBounds(120, 70, 150, 30);
        add(botaoSimular);

        JButton botaoVerDados = new JButton("Visualizar Dados");
        botaoVerDados.setBounds(120, 110, 150, 30);
        add(botaoVerDados);

        JButton botaoGrafico = new JButton("Ver Gráfico");
        botaoGrafico.setBounds(120, 150, 150, 30);
        add(botaoGrafico);

        JButton botaoExportar = new JButton("Exportar Relatório");
        botaoExportar.setBounds(120, 190, 150, 30);
        add(botaoExportar);

        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(120, 230, 150, 30);
        add(botaoSair);

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessaoUsuario.logout();
                dispose();
                new TelaLogin();
            }
        });


        setVisible(true);
    }
}
