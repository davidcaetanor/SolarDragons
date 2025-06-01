package view;

import model.Cliente;
import model.Usuario;
import model.SimulacaoEnergia;
import database.UsuarioDAO;
import database.ClienteDAO;
import database.SimulacaoEnergiaDAO;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class TelaADMSimulacoes extends JFrame {
    private JTable tabelaSimulacoes;
    private DefaultTableModel tableModel;

    public TelaADMSimulacoes() {
        setTitle("Simulações Registradas - SolarDragons");
        setMinimumSize(new Dimension(900, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new GridBagLayout());
        EstiloSolarDragons.aplicarFundo(getContentPane());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0; c.gridy = 0; c.insets = new Insets(18, 0, 12, 0); c.gridwidth = 2;
        JLabel logo = EstiloSolarDragons.criarLogo(110, 110,
                "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        add(logo, c);

        c.gridy = 1;
        JLabel titulo = new JLabel("Todas as simulações realizadas");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        add(titulo, c);

        String[] colunas = {
                "Usuário", "Cliente", "Conta (R$)", "Estado", "Potência (kWp)",
                "Módulos", "Custo (R$)", "Economia (5 anos)", "Payback"
        };
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaSimulacoes = new JTable(tableModel);

        tabelaSimulacoes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                } else {
                    c.setBackground(new Color(220, 230, 245));
                }
                c.setForeground(EstiloSolarDragons.AZUL_ESCURO);
                return c;
            }
        });
        tabelaSimulacoes.setRowHeight(28);
        JTableHeader header = tabelaSimulacoes.getTableHeader();
        header.setBackground(EstiloSolarDragons.AZUL_ESCURO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tabelaSimulacoes);
        scrollPane.setPreferredSize(new Dimension(800, 330));
        c.gridy = 2; c.insets = new Insets(10, 0, 18, 0);
        add(scrollPane, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        EstiloSolarDragons.aplicarFundo(painelBotoes);

        JButton botaoVoltar = new JButton("Voltar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoVoltar);
        botaoVoltar.setPreferredSize(new Dimension(140, 36));
        painelBotoes.add(botaoVoltar);

        c.gridy = 3;
        add(painelBotoes, c);

        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaADM();
        });

        atualizarTabela();

        setVisible(true);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();

        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = clienteDAO.listarPorUsuario(usuario.getCpf());
            for (Cliente c : clientes) {
                List<SimulacaoEnergia> simulacoes = simulacaoDAO.listarPorCliente(c.getId());
                for (SimulacaoEnergia s : simulacoes) {
                    tableModel.addRow(new Object[]{
                            usuario.getNome(),
                            c.getNome(),
                            String.format("%.2f", s.getValorContaReais()),
                            c.getEstado(),
                            String.format("%.2f", s.getPotenciaSistemaKw()),
                            s.getQuantidadeModulos(),
                            String.format("R$ %.2f", s.getCustoSistema()),
                            String.format("R$ %.2f", s.getEconomiaAnual() * 5),
                            formatarPayback(s.getPaybackAnos())
                    });
                }
            }
        }
    }

    private String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";
        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);
        if (anosInt == 0 && meses == 0) return "Menos de 1 mês";
        if (anosInt == 0) return meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return anosInt + (anosInt == 1 ? " ano" : " anos");
        return anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }
}
