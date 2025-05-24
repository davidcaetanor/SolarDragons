package view;

import service.SessaoUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaADM extends JFrame {

    public TelaADM() {
        setTitle("Painel Administrativo - SolarDragons");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel saudacao = new JLabel("Bem-vindo, ADM: " + SessaoUsuario.getUsuarioLogado().getNome());
        saudacao.setBounds(60, 20, 300, 30);
        add(saudacao);

        JButton botaoUsuarios = new JButton("Listar Usuários");
        botaoUsuarios.setBounds(120, 70, 150, 30);
        add(botaoUsuarios);

        JButton botaoSimulacoes = new JButton("Ver Simulações");
        botaoSimulacoes.setBounds(120, 110, 150, 30);
        add(botaoSimulacoes);

        JButton botaoExportar = new JButton("Exportar Relatório");
        botaoExportar.setBounds(120, 150, 150, 30);
        add(botaoExportar);

        JButton botaoParametros = new JButton("Parâmetros do Sistema");
        botaoParametros.setBounds(120, 190, 150, 30);
        add(botaoParametros);

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
