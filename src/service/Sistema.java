package service;

import view.GraficoEconomia;
import model.Cliente;
import model.Endereco;
import model.SimulacaoEnergia;

import java.io.PrintWriter;
import java.util.*;

public class Sistema {
    private final Scanner SC = new Scanner(System.in);
    private final List<Cliente> clientes = new ArrayList<>();

    public void iniciar() {
        int op;
        do {
            System.out.println("""
                            MENU DE ESCOLHA
                    1. Cadastrar Cliente
                    2. Simulação de energia
                    3. Buscar Cadastro
                    4. Ver gráfico de economia anual
                    5. Exportar Relatório CSV
                    0. Sair
                    """);
            System.out.print("Escolha uma opção: ");
            op = SC.nextInt();
            SC.nextLine();

            switch (op) {
                case 1 -> gerenciarCadastro();
                case 2 -> gerenciarSimulacao();
                case 3 -> buscarCadastro();
                case 4 -> {
                    boolean existeSimulacao = clientes.stream().anyMatch(c -> c.getSimulacao() != null);
                    if (!existeSimulacao) {
                        System.out.println("Nenhum cliente possui simulação registrada.");
                        System.out.println("Acesse a opção 2 para realizar uma simulação antes de visualizar o gráfico.");
                    } else {
                        GraficoEconomia.exibirGraficoEconomia(clientes);
                    }
                }
                case 5 -> exportarCSV();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Entrada inválida, insira uma opção entre 0 a 5");
            }
        } while (op != 0);
        SC.close();
    }

    public void gerenciarCadastro() {
        try {
            System.out.print("CPF (apenas números): ");
            String cpf = SC.nextLine().trim();

            if (!cpf.matches("\\d{11}")) {
                System.out.println("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
                return;
            }

            if (buscarClientePorCpf(cpf) != null) {
                System.out.println("CPF já cadastrado. Não é possível duplicar.");
                return;
            }

            System.out.print("Nome: ");
            String nome = SC.nextLine();

            System.out.print("CEP: ");
            String cep = SC.nextLine();

            System.out.print("Número da residência: ");
            String numero = SC.nextLine();

            Endereco endereco = ViaCEP.buscarEnderecoPorCEP(cep);
            Cliente cliente = new Cliente(cpf, nome);
            cliente.setLogradouro(endereco.getLogradouro());
            cliente.setBairro(endereco.getBairro());
            cliente.setCidade(endereco.getCidade());
            cliente.setEstado(endereco.getEstado());
            cliente.setNumero(numero);

            clientes.add(cliente);
            System.out.println("Cliente cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro de busca de CEP: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public void gerenciarSimulacao() {
        System.out.print("Digite o CPF do cliente para simulação: ");
        String cpf = SC.nextLine();
        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        try {
            System.out.print("Informe o valor médio da conta de luz (R$): ");
            double conta = SC.nextDouble();
            SC.nextLine();

            SimulacaoEnergia simulacao = new SimulacaoEnergia(cliente.getEstado(), conta);
            cliente.setSimulacao(simulacao);

            System.out.println("Simulação registrada:");
            System.out.printf("Consumo estimado: %.2f kWh/mês\n", simulacao.getConsumoEstimadoKwh());
            System.out.printf("Potência do sistema necessária: %.2f kWp\n", simulacao.getPotenciaSistemaKw());
            System.out.println("Quantidade de módulos solares: " + simulacao.getQuantidadeModulos());
            System.out.printf("Área necessária para instalação: %.2f m²\n", simulacao.getAreaNecessariaM2());
            System.out.printf("Custo estimado do sistema: R$ %.2f\n", simulacao.getCustoSistema());
            System.out.printf("Economia anual estimada: R$ %.2f\n", simulacao.getEconomiaAnual());
            System.out.println("Tempo estimado de retorno do investimento (payback): " + formatarPayback(simulacao.getPaybackAnos()));

        } catch (Exception e) {
            System.out.println("Erro na entrada de dados: " + e.getMessage());
            SC.nextLine();
        }
    }

    public void buscarCadastro() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = SC.nextLine();
        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("--- Dados do Cliente ---");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Logradouro: " + cliente.getLogradouro());
        System.out.println("Número: " + cliente.getNumero());
        System.out.println("Bairro: " + cliente.getBairro());
        System.out.println("Cidade: " + cliente.getCidade());
        System.out.println("Estado: " + cliente.getEstado());

        if (cliente.getSimulacao() != null) {
            SimulacaoEnergia s = cliente.getSimulacao();
            System.out.println("--- Simulação ---");
            System.out.printf("Consumo estimado: %.2f kWh/mês\n", s.getConsumoEstimadoKwh());
            System.out.printf("Potência do sistema: %.2f kWp\n", s.getPotenciaSistemaKw());
            System.out.println("Quantidade de módulos: " + s.getQuantidadeModulos());
            System.out.printf("Área necessária: %.2f m²\n", s.getAreaNecessariaM2());
            System.out.printf("Custo do sistema: R$ %.2f\n", s.getCustoSistema());
            System.out.printf("Economia anual: R$ %.2f\n", s.getEconomiaAnual());
            System.out.println("Payback estimado: " + formatarPayback(s.getPaybackAnos()));
        } else {
            System.out.println("Nenhuma simulação registrada.");
        }
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) return c;
        }
        return null;
    }

    public String formatarPayback(double anos) {
        if (anos < 0) return "Não aplicável";

        int anosInt = (int) anos;
        int meses = (int) Math.round((anos - anosInt) * 12);

        if (anosInt == 0 && meses == 0) return "Retorno de investimento imediato (menos de 1 mês)";
        if (anosInt == 0) return "Retorno de investimento em " + meses + (meses == 1 ? " mês" : " meses");
        if (meses == 0) return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos");
        return "Retorno de investimento em " + anosInt + (anosInt == 1 ? " ano" : " anos") + " e " + meses + (meses == 1 ? " mês" : " meses");
    }

    public void exportarCSV() {
        try (PrintWriter writer = new PrintWriter("relatorio-clientes.csv")) {
            writer.println("CPF,Nome,Cidade,Estado,Conta (R$),Potência (kWp),Módulos,Área (m²),Custo (R$),Economia (R$),Payback (anos)");

            for (Cliente c : clientes) {
                SimulacaoEnergia s = c.getSimulacao();
                if (s != null) {
                    writer.printf("%s,%s,%s,%s,%.2f,%.2f,%d,%.2f,%.2f,%.2f,%.2f\n",
                            c.getCpf(), c.getNome(), c.getCidade(), c.getEstado(),
                            s.getValorContaReais(), s.getPotenciaSistemaKw(), s.getQuantidadeModulos(),
                            s.getAreaNecessariaM2(), s.getCustoSistema(), s.getEconomiaAnual(), s.getPaybackAnos());
                }
            }

            System.out.println("Relatório exportado com sucesso para 'relatorio-clientes.csv'");

        } catch (Exception e) {
            System.out.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
}
