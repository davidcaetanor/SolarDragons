package service;

import model.Cliente;

import java.util.Scanner;

public class Sistema {
    private final Scanner SC = new Scanner(System.in);

    public void iniciar() {
        int op = -2;
        do{
            System.out.println("""
                            MENU DE ESCOLHA
                    1. Cadastrar Cliente
                    2. Simulação de energia
                    3. Buscar Cadastro
                    0. Sair
                    """);
            System.out.println("Escolha uma opção: ");
            op = SC.nextInt();
            switch(op){
                case 1:
                    gerenciarCadastro();
                    break;
                case 2:
                    gerenciarSimulacao();
                    break;
                case 3:
                    buscarCadastro();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Entrada inválida, insira uma opção entre 0 a 3");
                    break;
            }
        } while(op != 0);
        SC.close();
    }

    public void gerenciarCadastro() {
        System.out.println("Gerenciar");
    }

    public void gerenciarSimulacao() {
        System.out.println("Simulação");

    }

    public void buscarCadastro(){
        System.out.println("Buscando Cadastro");
    }
}
