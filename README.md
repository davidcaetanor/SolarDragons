# SolarDragons - Simulador de Economia com Energia Solar

**Projeto TCM - Simulador de Economia com Energia Solar **  
Professor: Túlio Cearamicoli Vivaldini

## Sobre o Projeto

Este sistema simula a economia gerada pelo uso de energia solar em locais. 
O usuário consegue cadastrar dados para complementar como (nome, CPF, endereço completo, consumo mensal, tarifa) e visualizar a economia estimada, incluindo o retorno sobre o investimento (payback).

## Funcionalidades

- Cadastro completo do cliente (nome, CPF, endereço, número da casa);
- Cálculo de economia com base em dados reais (potência do sistema, produção mensal);
- Cálculo de payback formatado em frases intuitivas;
- Geração de gráficos informativos sobre economia anual;
- Exportação de relatórios (PDF e CSV);
- Persistência de dados (arquivo ou banco de dados MySQL);
- Interface gráfica em Java (Swing);
- Integração com API ViaCEP para preenchimento automático do endereço utilizando, exemplo cep  " xxxxxxxx ". 

## Tecnologias Utilizadas

- Java 21
- Swing (interface gráfica)
- JDBC (conexão com banco de dados)
- MySQL + MySQL Workbench
- XChart (gráficos)
- JSON (persistência de dados temporária)
