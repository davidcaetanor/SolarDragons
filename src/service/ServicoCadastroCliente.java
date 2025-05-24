package service;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ServicoCadastroCliente {

    private static final List<Cliente> clientes = new ArrayList<>();

    public static boolean cadastrarOuAtualizarCliente(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            return false;
        }

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cliente.getCpf())) {
                clientes.set(i, cliente);
                return true;
            }
        }

        clientes.add(cliente);
        return true;
    }

    public static Cliente getClientePorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) return null;
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public static boolean clienteExiste(String cpf) {
        return getClientePorCpf(cpf) != null;
    }


    public static List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }


    public static boolean removerCliente(String cpf) {
        Cliente c = getClientePorCpf(cpf);
        if (c != null) {
            clientes.remove(c);
            return true;
        }
        return false;
    }
}
