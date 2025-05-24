package view;

import javax.swing.*;

public class TelaExportarRelatorios extends JFrame {
    public TelaExportarRelatorios() {
        setTitle("Exportar Relatórios");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Função em desenvolvimento!");
        label.setBounds(60, 60, 300, 30);
        add(label);

        setVisible(true);
    }
}
