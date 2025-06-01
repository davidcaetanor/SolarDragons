package view;

import javax.swing.*;
import java.awt.*;

public class TelaExportarRelatorios extends JFrame {
    public TelaExportarRelatorios() {
        setTitle("Exportar Relatórios");
        setMinimumSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("exporta relatorio");
        label.setBounds(60, 60, 300, 30);
        add(label);

        setVisible(true);
    }
}
