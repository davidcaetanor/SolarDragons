package view;

import model.Cliente;
import service.ServicoCadastroCliente;
import service.SessaoUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaGerenciarClientes extends JFrame {

    private JList<String> listaClientes;
    private DefaultListModel<String> modeloLista;
    private List<Cliente> clientes;

    public TelaGerenciarClientes() {
        setTitle("Gerenciar Clientes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        carregarClientes();

        JLabel labelTitulo = new JLabel("Clientes cadastrados:");
        labelTitulo.setBounds(20, 20, 200, 25);
        add(labelTitulo);

        modeloLista = new DefaultListModel<>();
        listaClientes = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaClientes);
        scrollPane.setBounds(20, 50, 440, 200);
        add(scrollPane);

        atualizarLista();

        JButton botaoAdicionar = new JButton("Adicionar Novo Cliente");
        botaoAdicionar.setBounds(20, 270, 200, 30);
        add(botaoAdicionar);

        JButton botaoEditar = new JButton("Editar Cliente");
        botaoEditar.setBounds(230, 270, 120, 30);
        add(botaoEditar);

        JButton botaoExcluir = new JButton("Excluir Cliente");
        botaoExcluir.setBounds(360, 270, 120, 30);
        add(botaoExcluir);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(180, 320, 120, 30);
        add(botaoVoltar);

        botaoAdicionar.addActionListener(e -> {
            dispose();
            new TelaCadastroCliente();
        });

        botaoEditar.addActionListener(e -> editarCliente());
        botaoExcluir.addActionListener(e -> excluirCliente());
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipalUsuario();
        });

        setVisible(true);
    }

    private void carregarClientes() {
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        clientes = ServicoCadastroCliente.listarClientesDoUsuario(cpfUsuario);
    }

    private void atualizarLista() {
        modeloLista.clear();
        for (Cliente c : clientes) {
            modeloLista.addElement(c.getNome() + " | CPF: " + c.getCpf());
        }
    }

    private void editarCliente() {
        int index = listaClientes.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente cliente = clientes.get(index);
        dispose();
        new TelaCadastroClienteEdicao(cliente);
    }

    private void excluirCliente() {
        int index = listaClientes.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente cliente = clientes.get(index);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o cliente: " + cliente.getNome() + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
            ServicoCadastroCliente.removerCliente(cpfUsuario, cliente.getCpf());
            carregarClientes();
            atualizarLista();
        }
    }
}
