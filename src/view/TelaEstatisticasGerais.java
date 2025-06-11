package view;

import model.Usuario;
import model.Cliente;
import model.SimulacaoEnergia;
import database.UsuarioDAO;
import database.ClienteDAO;
import database.SimulacaoEnergiaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class TelaEstatisticasGerais extends JFrame {
    public TelaEstatisticasGerais() {
        setTitle("Estatísticas Gerais - SolarDragons");
        setMinimumSize(new Dimension(900, 900));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        EstiloSolarDragons.aplicarFundo(getContentPane());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(16, 0, 12, 0);
        c.gridx = 0; c.gridwidth = 1;

        JLabel logo = EstiloSolarDragons.criarLogo(200, 200, "/resources/iconSolarDragons.png");
        c.gridy = 0;
        add(logo, c);

        JLabel titulo = new JLabel("Estatísticas Gerais");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1;
        c.insets = new Insets(60, 0, 0, 0);
        add(titulo, c);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();

        List<Usuario> usuarios = usuarioDAO.listar();
        int totalClientes = 0;
        int totalSimulacoes = 0;
        double economiaTotal = 0;
        Set<String> estadosCadastrados = new HashSet<>();

        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = clienteDAO.listarPorUsuario(usuario.getCpf());
            totalClientes += clientes.size();
            for (Cliente x : clientes) {

                if (x.getEstado() != null && !x.getEstado().trim().isEmpty()) {
                    estadosCadastrados.add(x.getEstado().trim().toUpperCase());
                }
                List<SimulacaoEnergia> simulacoes = simulacaoDAO.listarPorCliente(x.getId());
                totalSimulacoes += simulacoes.size();
                for (SimulacaoEnergia s : simulacoes) {
                    economiaTotal += s.getEconomiaAnual() * 5;
                }
            }
        }
        int totalEstados = estadosCadastrados.size();

        JPanel painelCards = new JPanel(new GridLayout(2, 2, 28, 28));
        painelCards.setOpaque(false);
        painelCards.setBorder(BorderFactory.createEmptyBorder(38, 32, 38, 32));
        c.gridy = 2; c.insets = new Insets(28, 0, 18, 0);
        add(painelCards, c);

        Color vermelhoCard = new Color(59, 34, 48);
        Color azulCard = new Color(35, 54, 68);
        Color roxoCard = new Color(49, 41, 66);
        Color begeCard = new Color(222, 211, 195);

        Font numFont = new Font("Arial", Font.BOLD, 34);
        Font titleFont = new Font("Arial", Font.PLAIN, 18);

        painelCards.add(cardEstatistica("Estados cadastrados", String.valueOf(totalEstados), roxoCard, numFont, titleFont));
        painelCards.add(cardEstatistica("Clientes cadastrados", String.valueOf(totalClientes), vermelhoCard, numFont, titleFont));
        painelCards.add(cardEstatistica("Simulações realizadas", String.valueOf(totalSimulacoes), azulCard, numFont, titleFont));
        painelCards.add(cardEstatistica("Economia potencial (5 anos)",
                "R$ " + String.format("%,.2f", economiaTotal), begeCard, numFont, titleFont, EstiloSolarDragons.AZUL_ESCURO));

        JLabel info = new JLabel("<html><center><i>Estes dados refletem o uso atual do sistema e o potencial de economia com as simulações realizadas.</i></center></html>");
        info.setFont(new Font("Arial", Font.ITALIC, 15));
        info.setForeground(new Color(110, 110, 120));
        c.gridy = 3; c.insets = new Insets(14,0,6,0);
        add(info, c);

        setVisible(true);
    }

    private JPanel cardEstatistica(String titulo, String valor, Color cor, Font fontValor, Font fontTitulo) {
        return cardEstatistica(titulo, valor, cor, fontValor, fontTitulo, Color.WHITE);
    }

    private JPanel cardEstatistica(String titulo, String valor, Color cor, Font fontValor, Font fontTitulo, Color corTexto) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(cor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 197, 150), 2, true),
                BorderFactory.createEmptyBorder(24, 18, 18, 18)
        ));

        JLabel lblValor = new JLabel(valor, JLabel.CENTER);
        lblValor.setFont(fontValor);
        lblValor.setForeground(corTexto);
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(fontTitulo);
        lblTitulo.setForeground(corTexto);

        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);
        return card;
    }
}
