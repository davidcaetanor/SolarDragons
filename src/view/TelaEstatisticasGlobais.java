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

public class TelaEstatisticasGlobais extends JFrame {
    public TelaEstatisticasGlobais() {
        setTitle("Estatísticas Globais");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        SimulacaoEnergiaDAO simulacaoDAO = new SimulacaoEnergiaDAO();

        List<Usuario> usuarios = usuarioDAO.listar();
        int totalUsuarios = usuarios.size();
        int totalClientes = 0;
        double economiaTotal = 0;
        int totalSimulacoes = 0;

        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = clienteDAO.listarPorUsuario(usuario.getCpf());
            totalClientes += clientes.size();
            for (Cliente c : clientes) {

                List<SimulacaoEnergia> simulacoes = simulacaoDAO.listarPorCliente(c.getId());
                totalSimulacoes += simulacoes.size();
                for (SimulacaoEnergia s : simulacoes) {
                    economiaTotal += s.getEconomiaAnual() * 5;
                }
            }
        }

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Total de usuários: " + totalUsuarios +
                "\nTotal de clientes: " + totalClientes +
                "\nTotal de simulações: " + totalSimulacoes +
                "\nEconomia total gerada (5 anos): R$ " + String.format("%.2f", economiaTotal));

        add(area, BorderLayout.CENTER);

        setVisible(true);
    }
}
