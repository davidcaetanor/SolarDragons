package service;

import model.Cliente;
import java.util.*;

public class ServicoCadastroCliente {

    /
    private static final Map<String, List<Cliente>> clientesPorUsuario = new HashMap<>();


    public static void adicionarCliente(String cpfUsuario, Cliente cliente) {
        if (cpfUsuario == null || cpfUsuario.isEmpty() || cliente == null) return;
        List<Cliente> lista = clientesPorUsuario.getOrDefault(cpfUsuario, new ArrayList<>());
        lista.add(cliente);
        clientesPorUsuario.put(cpfUsuario, lista);
    }


    public static List<Cliente> listarClientesDoUsuario(String cpfUsuario) {
        return clientesPorUsuario.getOrDefault(cpfUsuario, new ArrayList<>());
    }


    public static Cliente buscarClientePorCpfCliente(String cpfUsuario, String cpfCliente) {
        for (Cliente c : listarClientesDoUsuario(cpfUsuario)) {
            if (c.getCpf().equals(cpfCliente)) return c;
        }
        return null;
    }


    public static boolean clienteExiste(String cpfUsuario) {
        List<Cliente> lista = clientesPorUsuario.get(cpfUsuario);
        return lista != null && !lista.isEmpty();
    }


    public static boolean removerCliente(String cpfUsuario, String cpfCliente) {
        List<Cliente> lista = clientesPorUsuario.get(cpfUsuario);
        if (lista != null) {
            Iterator<Cliente> it = lista.iterator();
            while (it.hasNext()) {
                Cliente c = it.next();
                if (c.getCpf().equals(cpfCliente)) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean atualizarCliente(String cpfUsuario, Cliente clienteAtualizado) {
        List<Cliente> lista = clientesPorUsuario.get(cpfUsuario);
        if (lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getCpf().equals(clienteAtualizado.getCpf())) {
                    lista.set(i, clienteAtualizado);
                    return true;
                }
            }
        }
        return false;
    }
}
