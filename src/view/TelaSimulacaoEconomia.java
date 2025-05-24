package view;

import model.Cliente;
import model.SimulacaoEnergia;
import service.ServicoCadastroCliente;
import service.SessaoUsuario;

import javax.swing.*;
import java.util.List;

public class TelaSimulacaoEconomia extends JFrame {

    private JTextField campoConta;
    private JButton botaoSimular;
    private JButton botaoVoltar;
    private JTextArea areaResultado;

    public TelaSimulacaoEconomia() {
        setTitle("Simulação de Economia");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelConta = new JLabel("Valor médio da conta (R$):");
        labelConta.setBounds(40, 40, 180, 25);
        add(labelConta);

        campoConta = new JTextField();
        campoConta.setBounds(220, 40, 120, 25);
        add(campoConta);

        botaoSimular = new JButton("Simular");
        botaoSimular.setBounds(100, 90, 100, 30);
        add(botaoSimular);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(220, 90, 100, 30);
        add(botaoVoltar);

        areaResultado = new JTextArea();
        areaResultado.setBounds(40, 140, 360, 200);
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Resultados da Simulação"));
        add(areaResultado);

        botaoSimular.addActionListener(e -> simularEconomia());
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipalUsuario();
        });

        setVisible(true);
    }

    private void simularEconomia() {
        String valorContaStr = campoConta.getText().trim();

        if (valorContaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o valor médio da conta!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double valorConta;
        try {
            valorConta = Double.parseDouble(valorContaStr.replace(",", "."));
            if (valorConta <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpfUsuario = SessaoUsuario.getUsuarioLogado().getCpf();
        List<Cliente> clientes = ServicoCadastroCliente.listarClientesDoUsuario(cpfUsuario);

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre ao menos um cliente antes de simular.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Selecionar o cliente
        String[] opcoes = clientes.stream()
                .map(c -> c.getNome() + " - " + c.getCpf())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o cliente para simular:",
                "Selecionar Cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == null) return; // Usuário cancelou

        String cpfClienteSelecionado = escolha.substring(escolha.lastIndexOf(" - ") + 3).trim();
        Cliente cliente = ServicoCadastroCliente.buscarClientePorCpfCliente(cpfUsuario, cpfClienteSelecionado);

        if (cliente == null || cliente.getEstado().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dados do cliente incompletos. Cadastre/atualize o endereço antes de simular.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimulacaoEnergia simulacao = new SimulacaoEnergia(cliente.getEstado(), valorConta);
        cliente.setSimulacao(simulacao);
        ServicoCadastroCliente.atualizarCliente(cpfUsuario, cliente);

        String resultado = String.format(
                "Consumo estimado: %.2f kWh/mês\n" +
                        "Potência do sistema necessária: %.2f kWp\n" +
                        "Quantidade de módulos solares: %d\n" +
                        "Área necessária para instalação: %.2f m²\n" +
                        "Custo estimado do sistema: R$ %.2f\n" +
                        "Economia anual estimada: R$ %.2f\n" +
                        "Tempo estimado de retorno do investimento (payback): %s",
                simulacao.getConsumoEstimadoKwh(),
                simulacao.getPotenciaSistemaKw(),
                simulacao.getQuantidadeModulos(),
                simulacao.getAreaNecessariaM2(),
                simulacao.getCustoSistema(),
                simulacao.getEconomiaAnual(),
                formatarPayback(simulacao.getPaybackAnos())
        );

        areaResultado.setText(resultado);
    }

    private String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";
        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);
        if (anosInt == 0 && meses == 0) return "Retorno de investimento imediato (menos de 1 mês)";
        if (anosInt == 0) return "Retorno de investimento em " + meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos");
        return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }
}
