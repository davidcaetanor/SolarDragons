package view;

import service.ParametrosSistema;
import javax.swing.*;
import java.awt.*;

public class TelaParametrosSistema extends JFrame {

    private JTextField campoCustoKw, campoFator, campoPercentual;
    private JButton botaoSalvar, botaoCancelar;

    public TelaParametrosSistema() {
        setTitle("Parâmetros do Sistema - SolarDragons");
        setMinimumSize(new Dimension(700, 700));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());
        GridBagConstraints c = new GridBagConstraints();

        JLabel logo = EstiloSolarDragons.criarLogo(180, 180, "/resources/iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.insets = new Insets(24, 0, 10, 0);
        add(logo, c);

        JLabel titulo = new JLabel("Parâmetros do Sistema");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(0, 0, 32, 0);
        add(titulo, c);

        c.gridwidth = 1;
        c.insets = new Insets(12, 48, 8, 14); c.anchor = GridBagConstraints.WEST;

        JLabel labelCustoKw = new JLabel("Custo por kW (R$):");
        EstiloSolarDragons.estilizarLabel(labelCustoKw);
        c.gridx = 0; c.gridy = 2;
        add(labelCustoKw, c);

        campoCustoKw = new JTextField(String.valueOf(ParametrosSistema.getCustoPorKw()), 12);
        EstiloSolarDragons.estilizarCampo(campoCustoKw);
        c.gridx = 1;
        add(campoCustoKw, c);

        JLabel labelFator = new JLabel("Fator Economia:");
        EstiloSolarDragons.estilizarLabel(labelFator);
        c.gridx = 0; c.gridy = 3;
        add(labelFator, c);

        campoFator = new JTextField(String.valueOf(ParametrosSistema.getFatorEconomia()), 12);
        EstiloSolarDragons.estilizarCampo(campoFator);
        c.gridx = 1;
        add(campoFator, c);

        JLabel labelPercentual = new JLabel("Percentual Geração:");
        EstiloSolarDragons.estilizarLabel(labelPercentual);
        c.gridx = 0; c.gridy = 4;
        add(labelPercentual, c);

        campoPercentual = new JTextField(String.valueOf(ParametrosSistema.getPercentualGeracao()), 12);
        EstiloSolarDragons.estilizarCampo(campoPercentual);
        c.gridx = 1;
        add(campoPercentual, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 18));
        painelBotoes.setOpaque(false);

        botaoSalvar = new JButton("Salvar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoCancelar);

        Dimension btnSize = new Dimension(140, 38);
        botaoSalvar.setPreferredSize(btnSize);
        botaoCancelar.setPreferredSize(btnSize);

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; c.insets = new Insets(40, 0, 8, 0);
        add(painelBotoes, c);

        botaoSalvar.addActionListener(e -> salvarParametros());
        botaoCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void salvarParametros() {
        try {
            double custo = Double.parseDouble(campoCustoKw.getText());
            double fator = Double.parseDouble(campoFator.getText());
            double perc = Double.parseDouble(campoPercentual.getText());

            if (custo <= 0 || fator <= 0 || perc <= 0) {
                throw new IllegalArgumentException();
            }
            ParametrosSistema.setCustoPorKw(custo);
            ParametrosSistema.setFatorEconomia(fator);
            ParametrosSistema.setPercentualGeracao(perc);

            JOptionPane.showMessageDialog(this, "Parâmetros atualizados com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
