package view;

import model.Cliente;
import database.ClienteDAO;
import service.SessaoUsuario;
import service.ViaCEP;
import model.Endereco;

import javax.swing.*;

public class TelaCadastroClienteEdicao extends JFrame {

    private JTextField campoNome, campoCpf, campoEmail, campoCep, campoLogradouro, campoNumero, campoBairro, campoCidade, campoEstado;
    private JButton botaoBuscarCep, botaoSalvar, botaoCancelar;
    private String cpfCliente;

    public TelaCadastroClienteEdicao(String cpfCliente) {
        this.cpfCliente = cpfCliente;

        setTitle("Editar Cliente");
        setSize(480, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(30, 30, 100, 25);
        add(labelNome);
        campoNome = new JTextField();
        campoNome.setBounds(140, 30, 280, 25);
        add(campoNome);

        JLabel labelCpf = new JLabel("CPF do Cliente:");
        labelCpf.setBounds(30, 70, 100, 25);
        add(labelCpf);
        campoCpf = new JTextField();
        campoCpf.setBounds(140, 70, 180, 25);
        campoCpf.setEditable(false); // CPF não pode ser alterado
        add(campoCpf);

        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(30, 110, 100, 25);
        add(labelEmail);
        campoEmail = new JTextField();
        campoEmail.setBounds(140, 110, 280, 25);
        add(campoEmail);

        JLabel labelCep = new JLabel("CEP:");
        labelCep.setBounds(30, 150, 100, 25);
        add(labelCep);
        campoCep = new JTextField();
        campoCep.setBounds(140, 150, 90, 25);
        add(campoCep);

        botaoBuscarCep = new JButton("Buscar CEP");
        botaoBuscarCep.setBounds(240, 150, 120, 25);
        add(botaoBuscarCep);

        JLabel labelLogradouro = new JLabel("Logradouro:");
        labelLogradouro.setBounds(30, 190, 100, 25);
        add(labelLogradouro);
        campoLogradouro = new JTextField();
        campoLogradouro.setBounds(140, 190, 280, 25);
        add(campoLogradouro);

        JLabel labelNumero = new JLabel("Número:");
        labelNumero.setBounds(30, 230, 100, 25);
        add(labelNumero);
        campoNumero = new JTextField();
        campoNumero.setBounds(140, 230, 90, 25);
        add(campoNumero);

        JLabel labelBairro = new JLabel("Bairro:");
        labelBairro.setBounds(30, 270, 100, 25);
        add(labelBairro);
        campoBairro = new JTextField();
        campoBairro.setBounds(140, 270, 180, 25);
        add(campoBairro);

        JLabel labelCidade = new JLabel("Cidade:");
        labelCidade.setBounds(30, 310, 100, 25);
        add(labelCidade);
        campoCidade = new JTextField();
        campoCidade.setBounds(140, 310, 180, 25);
        add(campoCidade);

        JLabel labelEstado = new JLabel("Estado (UF):");
        labelEstado.setBounds(30, 350, 100, 25);
        add(labelEstado);
        campoEstado = new JTextField();
        campoEstado.setBounds(140, 350, 50, 25);
        add(campoEstado);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(100, 380, 100, 30);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(220, 380, 100, 30);
        add(botaoCancelar);

        botaoBuscarCep.addActionListener(e -> buscarCep());
        botaoSalvar.addActionListener(e -> salvarEdicao());
        botaoCancelar.addActionListener(e -> {
            dispose();
            new TelaGerenciarClientes();
        });

        carregarCliente();

        setVisible(true);
    }

    private void carregarCliente() {
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCpfCliente(cpfUsuario, cpfCliente);

        if (cliente != null) {
            campoNome.setText(cliente.getNome());
            campoCpf.setText(cliente.getCpf());
            campoEmail.setText(cliente.getEmail());
            campoLogradouro.setText(cliente.getLogradouro());
            campoNumero.setText(cliente.getNumero());
            campoBairro.setText(cliente.getBairro());
            campoCidade.setText(cliente.getCidade());
            campoEstado.setText(cliente.getEstado());
            campoCep.setText(cliente.getCep());
        }
    }

    private void buscarCep() {
        String cep = campoCep.getText().trim();
        if (cep.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CEP!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Endereco endereco = ViaCEP.buscarEnderecoPorCEP(cep);
            campoLogradouro.setText(endereco.getLogradouro());
            campoBairro.setText(endereco.getBairro());
            campoCidade.setText(endereco.getCidade());
            campoEstado.setText(endereco.getEstado());
            campoLogradouro.setEditable(endereco.getLogradouro().isEmpty());
            campoBairro.setEditable(endereco.getBairro().isEmpty());
            campoCidade.setEditable(endereco.getCidade().isEmpty());
            campoEstado.setEditable(endereco.getEstado().isEmpty());
            if (endereco.getLogradouro().isEmpty() || endereco.getBairro().isEmpty()
                    || endereco.getCidade().isEmpty() || endereco.getEstado().isEmpty()) {
                JOptionPane.showMessageDialog(this, "CEP encontrado, mas alguns campos não foram preenchidos. Complete manualmente!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar CEP: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarEdicao() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String logradouro = campoLogradouro.getText().trim();
        String numero = campoNumero.getText().trim();
        String bairro = campoBairro.getText().trim();
        String cidade = campoCidade.getText().trim();
        String estado = campoEstado.getText().trim().toUpperCase();
        String cep = campoCep.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || logradouro.isEmpty() ||
                numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (estado.length() != 2) {
            JOptionPane.showMessageDialog(this, "Informe o estado com 2 letras (UF)", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteExistente = clienteDAO.buscarPorCpfCliente(cpfUsuario, cpfCliente);
        if (clienteExistente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado para edição!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!email.equals(clienteExistente.getEmail()) && clienteDAO.emailExisteParaUsuario(email, cpfUsuario)) {
            JOptionPane.showMessageDialog(this, "Já existe um cliente com este e-mail cadastrado para o seu usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente clienteEditado = new Cliente(cpfCliente, nome);
        clienteEditado.setEmail(email);
        clienteEditado.setLogradouro(logradouro);
        clienteEditado.setNumero(numero);
        clienteEditado.setBairro(bairro);
        clienteEditado.setCidade(cidade);
        clienteEditado.setEstado(estado);
        clienteEditado.setCep(cep);
        clienteEditado.setCpfUsuario(cpfUsuario);

        boolean atualizado = clienteDAO.atualizar(clienteEditado, cpfUsuario);
        if (atualizado) {
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
            dispose();
            new TelaGerenciarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
