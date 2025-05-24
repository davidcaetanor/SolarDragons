package view;

import model.Cliente;
import service.ServicoCadastroCliente;
import service.SessaoUsuario;
import javax.swing.*;

public class TelaCadastroCliente extends JFrame {

    private JTextField campoNome;
    private JTextField campoLogradouro;
    private JTextField campoNumero;
    private JTextField campoBairro;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(30, 30, 100, 25);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(140, 30, 220, 25);
        add(campoNome);

        JLabel labelLogradouro = new JLabel("Logradouro:");
        labelLogradouro.setBounds(30, 70, 100, 25);
        add(labelLogradouro);

        campoLogradouro = new JTextField();
        campoLogradouro.setBounds(140, 70, 220, 25);
        add(campoLogradouro);

        JLabel labelNumero = new JLabel("Número:");
        labelNumero.setBounds(30, 110, 100, 25);
        add(labelNumero);

        campoNumero = new JTextField();
        campoNumero.setBounds(140, 110, 220, 25);
        add(campoNumero);

        JLabel labelBairro = new JLabel("Bairro:");
        labelBairro.setBounds(30, 150, 100, 25);
        add(labelBairro);

        campoBairro = new JTextField();
        campoBairro.setBounds(140, 150, 220, 25);
        add(campoBairro);

        JLabel labelCidade = new JLabel("Cidade:");
        labelCidade.setBounds(30, 190, 100, 25);
        add(labelCidade);

        campoCidade = new JTextField();
        campoCidade.setBounds(140, 190, 220, 25);
        add(campoCidade);

        JLabel labelEstado = new JLabel("Estado (UF):");
        labelEstado.setBounds(30, 230, 100, 25);
        add(labelEstado);

        campoEstado = new JTextField();
        campoEstado.setBounds(140, 230, 50, 25);
        add(campoEstado);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(90, 280, 100, 35);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(210, 280, 100, 35);
        add(botaoCancelar);


        carregarDadosCliente();

        botaoSalvar.addActionListener(e -> salvarCliente());
        botaoCancelar.addActionListener(e -> {
            dispose();

        });

        setVisible(true);
    }

    private void carregarDadosCliente() {
        String cpf = SessaoUsuario.getUsuarioLogado().getCpf();
        Cliente cliente = service.ServicoCadastroCliente.getClientePorCpf(cpf);
        if (cliente != null) {
            campoNome.setText(cliente.getNome());
            campoLogradouro.setText(cliente.getLogradouro());
            campoNumero.setText(cliente.getNumero());
            campoBairro.setText(cliente.getBairro());
            campoCidade.setText(cliente.getCidade());
            campoEstado.setText(cliente.getEstado());
        } else {
            campoNome.setText(SessaoUsuario.getUsuarioLogado().getNome());
        }
    }

    private void salvarCliente() {
        String cpf = SessaoUsuario.getUsuarioLogado().getCpf();
        String nome = campoNome.getText().trim();
        String logradouro = campoLogradouro.getText().trim();
        String numero = campoNumero.getText().trim();
        String bairro = campoBairro.getText().trim();
        String cidade = campoCidade.getText().trim();
        String estado = campoEstado.getText().trim().toUpperCase();


        if (nome.isEmpty() || logradouro.isEmpty() || numero.isEmpty() ||
                bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (estado.length() != 2) {
            JOptionPane.showMessageDialog(this, "Informe o estado com 2 letras (UF)", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(cpf, nome);
        cliente.setLogradouro(logradouro);
        cliente.setNumero(numero);
        cliente.setBairro(bairro);
        cliente.setCidade(cidade);
        cliente.setEstado(estado);

        boolean sucesso = service.ServicoCadastroCliente.cadastrarOuAtualizarCliente(cliente);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
