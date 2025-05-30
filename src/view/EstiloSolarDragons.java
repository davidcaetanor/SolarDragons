package view;

import javax.swing.*;
import java.awt.*;

public class EstiloSolarDragons {

    // Cores extraídas do fundo e logo
    public static final Color FUNDO_BEGE = new Color(248, 241, 227); // igual ao fundo do logo
    public static final Color AZUL_ESCURO = new Color(29, 40, 56);   // Azul profundo do logo
    public static final Color CINZA_CAMPO = new Color(246, 242, 230); // campos de texto

    public static final Font TITULO = new Font("Serif", Font.BOLD, 28);
    public static final Font LABEL = new Font("Arial", Font.BOLD, 14);
    public static final Font CAMPO = new Font("Arial", Font.PLAIN, 16);
    public static final Font BOTAO = new Font("Arial", Font.BOLD, 16);


    public static void aplicarFundo(Container c) {
        c.setBackground(FUNDO_BEGE);
    }


    public static void estilizarBotaoPrincipal(JButton botao) {
        botao.setBackground(AZUL_ESCURO);
        botao.setForeground(Color.WHITE);
        botao.setFont(BOTAO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }


    public static void estilizarBotaoSecundario(JButton botao) {
        botao.setBackground(FUNDO_BEGE);
        botao.setForeground(AZUL_ESCURO);
        botao.setFont(BOTAO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(AZUL_ESCURO, 2));
    }


    public static void estilizarCampo(JTextField campo) {
        campo.setFont(CAMPO);
        campo.setForeground(AZUL_ESCURO);
        campo.setBackground(CINZA_CAMPO);
        campo.setBorder(BorderFactory.createLineBorder(AZUL_ESCURO, 2, true));
    }
    public static void estilizarCampo(JPasswordField campo) {
        campo.setFont(CAMPO);
        campo.setForeground(AZUL_ESCURO);
        campo.setBackground(CINZA_CAMPO);
        campo.setBorder(BorderFactory.createLineBorder(AZUL_ESCURO, 2, true));
    }


    public static JLabel criarLogo(int largura, int altura, String caminho) {
        try {
            ImageIcon icon = new ImageIcon(caminho);
            Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            return new JLabel("Logo");
        }
    }
}
