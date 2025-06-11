# SolarDragons – Simulador de Economia com Energia Solar

**Projeto – Simulador de Economia com Energia Solar**  
Professor: Túlio Cearamicoli Vivaldini

## Sobre

SolarDragons é um sistema para simular a economia gerada pelo uso de energia solar em residências.
Permite cadastrar usuários, controlar clientes e registrar as simulações de economia.
O programa exibe a estimativa de retorno do investimento (payback) e apresenta gráficos de economia.

## Funcionalidades

- Cadastro de usuários com CPF, nome, email e senha
- Login por CPF e senha
- Perfis de usuário e administrador
- Gerenciamento de clientes com nome, CPF e endereço completo
- Simulação de economia e cálculo do payback
- Histórico de simulações armazenado em banco
- Gráfico de economia x investimento (5 anos)
- Ajuste de parâmetros do sistema (ADM)
- Estatísticas gerais para administradores
- Interface gráfica em Java Swing
- Integração com ViaCEP para preencher endereços
- Persistência em MySQL

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
