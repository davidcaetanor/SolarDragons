package view;

import javax.swing.*;
import java.awt.*;

public class EstiloSolarDragons {

    public static final Color FUNDO_BEGE = new Color(248, 241, 227);
    public static final Color AZUL_ESCURO = new Color(29, 40, 56);
    public static final Color CINZA_CAMPO = new Color(246, 242, 230);

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
            java.net.URL url = EstiloSolarDragons.class.getResource(caminho);
            if (url == null) {
                return new JLabel("Logo");
            }
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            return new JLabel("Logo");
        }
    }
    public static void estilizarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(AZUL_ESCURO);
    }
    public static ImageIcon getIcon(int largura, int altura, String caminho) {
        try {
            java.net.URL url = EstiloSolarDragons.class.getResource(caminho);
            if (url == null) {
                return new ImageIcon();
            }
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }
}
