package view;

import model.Cliente;
import model.ValidadorCPF;
import database.ClienteDAO;
import service.SessaoUsuario;
import service.ViaCEP;
import model.Endereco;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCliente extends JFrame {
    private JTextField campoNome, campoCpfCliente, campoEmail, campoCep, campoLogradouro, campoNumero, campoBairro, campoCidade, campoEstado;
    private JButton botaoBuscarCep, botaoSalvar, botaoCancelar;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 800));
        setLocationRelativeTo(null);
        setResizable(true);

        EstiloSolarDragons.aplicarFundo(getContentPane());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel logo = EstiloSolarDragons.criarLogo(
                120, 120, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        c.insets = new Insets(20, 0, 12, 0);
        add(logo, c);

        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(0, 0, 30, 0);
        add(titulo, c);

        c.gridwidth = 1;
        c.insets = new Insets(7, 35, 2, 10); c.anchor = GridBagConstraints.WEST;

        JLabel labelNome = new JLabel("Nome:");
        EstiloSolarDragons.estilizarLabel(labelNome);
        c.gridx = 0; c.gridy = 2;
        add(labelNome, c);

        campoNome = new JTextField(24);
        EstiloSolarDragons.estilizarCampo(campoNome);
        c.gridx = 1;
        add(campoNome, c);

        JLabel labelCpfCliente = new JLabel("CPF do Cliente:");
        EstiloSolarDragons.estilizarLabel(labelCpfCliente);
        c.gridx = 0; c.gridy++;
        add(labelCpfCliente, c);

        campoCpfCliente = new JTextField(14);
        EstiloSolarDragons.estilizarCampo(campoCpfCliente);
        c.gridx = 1;
        add(campoCpfCliente, c);

        JLabel labelEmail = new JLabel("Email:");
        EstiloSolarDragons.estilizarLabel(labelEmail);
        c.gridx = 0; c.gridy++;
        add(labelEmail, c);

        campoEmail = new JTextField(24);
        EstiloSolarDragons.estilizarCampo(campoEmail);
        c.gridx = 1;
        add(campoEmail, c);

        JLabel labelCep = new JLabel("CEP:");
        EstiloSolarDragons.estilizarLabel(labelCep);
        c.gridx = 0; c.gridy++;
        add(labelCep, c);

        JPanel painelCep = new JPanel(new BorderLayout());
        painelCep.setOpaque(false);
        campoCep = new JTextField(8);
        EstiloSolarDragons.estilizarCampo(campoCep);
        painelCep.add(campoCep, BorderLayout.CENTER);

        botaoBuscarCep = new JButton("Buscar CEP");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoBuscarCep);
        botaoBuscarCep.setPreferredSize(new Dimension(120, 30));
        painelCep.add(botaoBuscarCep, BorderLayout.EAST);

        c.gridx = 1;
        add(painelCep, c);

        JLabel labelLogradouro = new JLabel("Logradouro:");
        EstiloSolarDragons.estilizarLabel(labelLogradouro);
        c.gridx = 0; c.gridy++;
        add(labelLogradouro, c);

        campoLogradouro = new JTextField(22);
        EstiloSolarDragons.estilizarCampo(campoLogradouro);
        c.gridx = 1;
        add(campoLogradouro, c);

        JLabel labelNumero = new JLabel("Número:");
        EstiloSolarDragons.estilizarLabel(labelNumero);
        c.gridx = 0; c.gridy++;
        add(labelNumero, c);

        campoNumero = new JTextField(8);
        EstiloSolarDragons.estilizarCampo(campoNumero);
        c.gridx = 1;
        add(campoNumero, c);

        JLabel labelBairro = new JLabel("Bairro:");
        EstiloSolarDragons.estilizarLabel(labelBairro);
        c.gridx = 0; c.gridy++;
        add(labelBairro, c);

        campoBairro = new JTextField(18);
        EstiloSolarDragons.estilizarCampo(campoBairro);
        c.gridx = 1;
        add(campoBairro, c);

        JLabel labelCidade = new JLabel("Cidade:");
        EstiloSolarDragons.estilizarLabel(labelCidade);
        c.gridx = 0; c.gridy++;
        add(labelCidade, c);

        campoCidade = new JTextField(18);
        EstiloSolarDragons.estilizarCampo(campoCidade);
        c.gridx = 1;
        add(campoCidade, c);

        JLabel labelEstado = new JLabel("Estado (UF):");
        EstiloSolarDragons.estilizarLabel(labelEstado);
        c.gridx = 0; c.gridy++;
        add(labelEstado, c);

        campoEstado = new JTextField(3);
        EstiloSolarDragons.estilizarCampo(campoEstado);
        c.gridx = 1;
        add(campoEstado, c);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
        painelBotoes.setOpaque(false);

        botaoSalvar = new JButton("Salvar");
        EstiloSolarDragons.estilizarBotaoPrincipal(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoCancelar);

        Dimension tamanhoBotao = new Dimension(130, 40);
        botaoSalvar.setPreferredSize(tamanhoBotao);
        botaoCancelar.setPreferredSize(tamanhoBotao);

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        c.gridx = 0; c.gridy++; c.gridwidth = 2; c.insets = new Insets(32, 0, 8, 0);
        add(painelBotoes, c);

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
        if (!ValidadorCPF.isCPF(cpfCliente)) {
            JOptionPane.showMessageDialog(this, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Informe um email válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();

        ClienteDAO clienteDAO = new ClienteDAO();
        if (clienteDAO.emailExisteParaUsuario(email, cpfUsuario)) {
            JOptionPane.showMessageDialog(this, "Já existe um cliente com este e-mail para o seu usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (clienteDAO.cpfExisteParaUsuario(cpfCliente, cpfUsuario)) {
            JOptionPane.showMessageDialog(this, "Já existe um cliente com este CPF para o seu usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        cliente.setCpfUsuario(cpfUsuario);

        boolean cadastrado = clienteDAO.cadastrar(cliente, cpfUsuario);
        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            dispose();
            new TelaGerenciarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente! Verifique se já existe cliente com esse CPF ou e-mail para você.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
