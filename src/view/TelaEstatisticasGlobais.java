package view;

import model.Usuario;
import model.Cliente;
import service.AutenticacaoUser;
import service.ServicoCadastroCliente;

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

        List<Usuario> usuarios = AutenticacaoUser.listarUsuarios();
        int totalUsuarios = usuarios.size();
        int totalClientes = 0;
        double economiaTotal = 0;
        int totalSimulacoes = 0;

        for (Usuario usuario : usuarios) {
            List<Cliente> clientes = ServicoCadastroCliente.listarClientesDoUsuario(usuario.getCpf());
            totalClientes += clientes.size();
            for (Cliente c : clientes) {
                if (c.getSimulacao() != null) {
                    totalSimulacoes++;
                    economiaTotal += c.getSimulacao().getEconomiaAnual() * 5;
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
