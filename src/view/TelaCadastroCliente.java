package view;

import model.Cliente;
import service.ServicoCadastroCliente;
import service.SessaoUsuario;
import service.ViaCEP;
import model.Endereco;

import javax.swing.*;

public class TelaCadastroCliente extends JFrame {

    private JTextField campoNome, campoCpfCliente, campoEmail, campoCep, campoLogradouro, campoNumero, campoBairro, campoCidade, campoEstado;
    private JButton botaoBuscarCep, botaoSalvar, botaoCancelar;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(500, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(30, 30, 100, 25);
        add(labelNome);
        campoNome = new JTextField();
        campoNome.setBounds(140, 30, 280, 25);
        add(campoNome);

        JLabel labelCpfCliente = new JLabel("CPF do Cliente:");
        labelCpfCliente.setBounds(30, 70, 100, 25);
        add(labelCpfCliente);
        campoCpfCliente = new JTextField();
        campoCpfCliente.setBounds(140, 70, 180, 25);
        add(campoCpfCliente);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(30, 110, 100, 25);
        add(labelEmail);
        campoEmail = new JTextField();
        campoEmail.setBounds(140, 110, 180, 25);
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
        botaoSalvar.setBounds(120, 400, 100, 35);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(250, 400, 100, 35);
        add(botaoCancelar);

        botaoBuscarCep.addActionListener(e -> buscarCep());

        botaoSalvar.addActionListener(e -> salvarCliente());
        botaoCancelar.addActionListener(e -> {
            dispose();
            new TelaGerenciarClientes();
        });

        setVisible(true);
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

    private void salvarCliente() {
        String nome = campoNome.getText().trim();
        String cpfCliente = campoCpfCliente.getText().trim();
        String email = campoEmail.getText().trim();
        String cep = campoCep.getText().trim();
        String logradouro = campoLogradouro.getText().trim();
        String numero = campoNumero.getText().trim();
        String bairro = campoBairro.getText().trim();
        String cidade = campoCidade.getText().trim();
        String estado = campoEstado.getText().trim().toUpperCase();

        if (nome.isEmpty() || cpfCliente.isEmpty() || email.isEmpty() || cep.isEmpty() || logradouro.isEmpty() ||
                numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (estado.length() != 2) {
            JOptionPane.showMessageDialog(this, "Informe o estado com 2 letras (UF)", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!cpfCliente.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "CPF do cliente deve conter 11 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Informe um email válido para o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(cpfCliente, nome);
        cliente.setEmail(email);
        cliente.setLogradouro(logradouro);
        cliente.setNumero(numero);
        cliente.setBairro(bairro);
        cliente.setCidade(cidade);
        cliente.setEstado(estado);
        cliente.setCep(cep);

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        cliente.setCpfUsuario(cpfUsuario);

        boolean cadastrado = ServicoCadastroCliente.adicionarCliente(cliente, cpfUsuario);
        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            dispose();
            new TelaGerenciarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente! Verifique se já existe cliente com esse CPF para você.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
