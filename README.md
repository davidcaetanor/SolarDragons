# SolarDragons – Simulador de Economia com Energia Solar

**Projeto – Simulador de Economia com Energia Solar**  
Professor: Túlio Cearamicoli Vivaldini

## Sobre

SolarDragons é um sistema para simular a economia gerada pelo uso de energia solar em residências. 
O usuário pode cadastrar clientes, inserir dados de consumo mensal e endereço. 
Simular a economia mensal e anual, calcular o tempo de retorno do investimento (payback) e visualizar os resultados por meio de gráficos.

## Funcionalidades

- Cadastro de cliente com nome, CPF e endereço completo (incluindo número)
- Simulação de economia baseada em dados reais
- Cálculo e exibição do payback
- Geração de gráficos sobre economia (Estipulamos 5 anos)
- Persistência dos dados no banco MySQL
- Interface gráfica em Java Swing
- Integração com ViaCEP para preenchimento de endereço por CEP

## Tecnologias

- Java 21
- Swing
- JDBC
- MySQL
- XChart
- JSON (para persistência temporária)

## Como usar

1. Clone o repositório.
2. Configure o banco MySQL usando o script em `database/solardragons.sql`
3. Ajuste as credenciais no arquivo `ConexaoMySQL.java`
4. Compile e execute o projeto na sua IDE
5. Use a interface gráfica para cadastrar, simular e visualizar resultados

## Requisitos
- Java 21
- MySQL
