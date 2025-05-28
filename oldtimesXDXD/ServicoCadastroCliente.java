package service;

import database.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ServicoCadastroCliente {

    private static final ClienteDAO clienteDAO = new ClienteDAO();


    public static boolean adicionarCliente(Cliente cliente, String cpfUsuario) {
        cliente.setCpfUsuario(cpfUsuario);
        return clienteDAO.inserirCliente(cliente);
    }


    public static List<Cliente> listarClientesDoUsuario(String cpfUsuario) {
        return clienteDAO.listarClientesDoUsuario(cpfUsuario);
    }


    public static Cliente buscarClientePorCpfCliente(String cpfUsuario, String cpfCliente) {
        return clienteDAO.buscarClientePorCpf(cpfCliente, cpfUsuario);
    }


    public static boolean atualizarCliente(String cpfUsuario, Cliente clienteAtualizado) {
        clienteAtualizado.setCpfUsuario(cpfUsuario);
        return clienteDAO.atualizarCliente(clienteAtualizado);
    }


    public static boolean removerCliente(String cpfUsuario, String cpfCliente) {
        return clienteDAO.removerCliente(cpfCliente, cpfUsuario);
    }


    public static boolean clienteExiste(String cpfUsuario) {
        List<Cliente> lista = clienteDAO.listarClientesDoUsuario(cpfUsuario);
        return lista != null && !lista.isEmpty();
    }
}
