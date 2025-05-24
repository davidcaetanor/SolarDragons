package view;

import service.ParametrosSistema;

import javax.swing.*;

public class TelaParametrosSistema extends JFrame {

    private JTextField campoCustoKw, campoFator, campoPercentual;
    private JButton botaoSalvar, botaoCancelar;

    public TelaParametrosSistema() {
        setTitle("Parâmetros do Sistema");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelCustoKw = new JLabel("Custo por kW (R$):");
        labelCustoKw.setBounds(30, 30, 150, 25);
        add(labelCustoKw);

        campoCustoKw = new JTextField(String.valueOf(ParametrosSistema.getCustoPorKw()));
        campoCustoKw.setBounds(200, 30, 120, 25);
        add(campoCustoKw);

        JLabel labelFator = new JLabel("Fator Economia:");
        labelFator.setBounds(30, 70, 150, 25);
        add(labelFator);

        campoFator = new JTextField(String.valueOf(ParametrosSistema.getFatorEconomia()));
        campoFator.setBounds(200, 70, 120, 25);
        add(campoFator);

        JLabel labelPercentual = new JLabel("Percentual Geração:");
        labelPercentual.setBounds(30, 110, 150, 25);
        add(labelPercentual);

        campoPercentual = new JTextField(String.valueOf(ParametrosSistema.getPercentualGeracao()));
        campoPercentual.setBounds(200, 110, 120, 25);
        add(campoPercentual);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(80, 170, 100, 35);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(200, 170, 100, 35);
        add(botaoCancelar);

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
