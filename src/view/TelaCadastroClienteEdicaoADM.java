package view;

import model.Cliente;
import database.ClienteDAO;
import service.ViaCEP;
import model.Endereco;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroClienteEdicaoADM extends JFrame {

    private JTextField campoNome, campoCpf, campoEmail, campoCep, campoLogradouro, campoNumero, campoBairro, campoCidade, campoEstado;
    private JButton botaoBuscarCep, botaoSalvar, botaoCancelar;
    private String cpfCliente, cpfUsuarioResponsavel;

    public TelaCadastroClienteEdicaoADM(String cpfCliente, String cpfUsuarioResponsavel) {
        this.cpfCliente = cpfCliente;
        this.cpfUsuarioResponsavel = cpfUsuarioResponsavel;

        setTitle("Editar Cliente (ADM)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 800));
        setLocationRelativeTo(null);
        setResizable(true);

        EstiloSolarDragons.aplicarFundo(getContentPane());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel logo = EstiloSolarDragons.criarLogo(140, 140, "C:\\Users\\david\\IdeaProjects\\SolarDragons\\src\\resources\\iconSolarDragons.png");
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        c.insets = new Insets(20, 0, 10, 0); c.anchor = GridBagConstraints.CENTER;
        add(logo, c);

        JLabel titulo = new JLabel("Editar Cliente (ADM)");
        titulo.setFont(EstiloSolarDragons.TITULO);
        titulo.setForeground(EstiloSolarDragons.AZUL_ESCURO);
        c.gridy = 1; c.insets = new Insets(0, 0, 30, 0);
        add(titulo, c);

        c.gridwidth = 1; c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(7, 35, 2, 10);

        int linha = 2;

        c.gridx = 0; c.gridy = linha;
        JLabel labelNome = new JLabel("Nome:");
        EstiloSolarDragons.estilizarLabel(labelNome);
        add(labelNome, c);
        c.gridx = 1;
        campoNome = new JTextField(22);
        EstiloSolarDragons.estilizarCampo(campoNome);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoNome, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelCpf = new JLabel("CPF do Cliente:");
        EstiloSolarDragons.estilizarLabel(labelCpf);
        add(labelCpf, c);
        c.gridx = 1;
        campoCpf = new JTextField(15);
        campoCpf.setEditable(false);
        EstiloSolarDragons.estilizarCampo(campoCpf);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoCpf, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelEmail = new JLabel("E-mail:");
        EstiloSolarDragons.estilizarLabel(labelEmail);
        add(labelEmail, c);
        c.gridx = 1;
        campoEmail = new JTextField(22);
        EstiloSolarDragons.estilizarCampo(campoEmail);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoEmail, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelCep = new JLabel("CEP:");
        EstiloSolarDragons.estilizarLabel(labelCep);
        add(labelCep, c);
        c.gridx = 1;
        JPanel painelCep = new JPanel(new BorderLayout(5,0));
        painelCep.setOpaque(false);
        campoCep = new JTextField(8);
        EstiloSolarDragons.estilizarCampo(campoCep);
        painelCep.add(campoCep, BorderLayout.CENTER);

        botaoBuscarCep = new JButton("Buscar CEP");
        EstiloSolarDragons.estilizarBotaoSecundario(botaoBuscarCep);
        botaoBuscarCep.setPreferredSize(new Dimension(120, 30));
        painelCep.add(botaoBuscarCep, BorderLayout.EAST);

        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(painelCep, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelLogradouro = new JLabel("Logradouro:");
        EstiloSolarDragons.estilizarLabel(labelLogradouro);
        add(labelLogradouro, c);
        c.gridx = 1;
        campoLogradouro = new JTextField(22);
        EstiloSolarDragons.estilizarCampo(campoLogradouro);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoLogradouro, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelNumero = new JLabel("Número:");
        EstiloSolarDragons.estilizarLabel(labelNumero);
        add(labelNumero, c);
        c.gridx = 1;
        campoNumero = new JTextField(8);
        EstiloSolarDragons.estilizarCampo(campoNumero);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoNumero, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelBairro = new JLabel("Bairro:");
        EstiloSolarDragons.estilizarLabel(labelBairro);
        add(labelBairro, c);
        c.gridx = 1;
        campoBairro = new JTextField(18);
        EstiloSolarDragons.estilizarCampo(campoBairro);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoBairro, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelCidade = new JLabel("Cidade:");
        EstiloSolarDragons.estilizarLabel(labelCidade);
        add(labelCidade, c);
        c.gridx = 1;
        campoCidade = new JTextField(18);
        EstiloSolarDragons.estilizarCampo(campoCidade);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        add(campoCidade, c);

        c.gridx = 0; c.gridy = ++linha; c.fill = GridBagConstraints.NONE; c.weightx = 0;
        JLabel labelEstado = new JLabel("Estado (UF):");
        EstiloSolarDragons.estilizarLabel(labelEstado);
        add(labelEstado, c);
        c.gridx = 1;
        campoEstado = new JTextField(3);
        EstiloSolarDragons.estilizarCampo(campoEstado);
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
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

        c.gridx = 0; c.gridy = ++linha; c.gridwidth = 2; c.insets = new Insets(32, 0, 8, 0);
        c.fill = GridBagConstraints.NONE;
        add(painelBotoes, c);

        botaoBuscarCep.addActionListener(e -> buscarCep());
        botaoSalvar.addActionListener(e -> salvarEdicao());
        botaoCancelar.addActionListener(e -> {
            TelaUtil.voltarParaPainelUsuario(this);
        });

        carregarCliente();
        setVisible(true);
    }

    private void carregarCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCpfCliente(cpfUsuarioResponsavel, cpfCliente);

        if (cliente != null) {
            campoNome.setText(cliente.getNome());
            campoCpf.setText(cliente.getCpf());
            campoCpf.setEditable(false);
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

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteExistente = clienteDAO.buscarPorCpfCliente(cpfUsuarioResponsavel, cpfCliente);
        if (clienteExistente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado para edição!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.equals(clienteExistente.getEmail()) && clienteDAO.emailExisteParaUsuario(email, cpfUsuarioResponsavel)) {
            JOptionPane.showMessageDialog(this, "Já existe um cliente com este e-mail cadastrado para o usuário responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        clienteEditado.setCpfUsuario(cpfUsuarioResponsavel);

        boolean atualizado = clienteDAO.atualizar(clienteEditado, cpfUsuarioResponsavel);
        if (atualizado) {
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
            dispose();
            TelaUtil.voltarParaPainelUsuario(this);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
