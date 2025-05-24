package service;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class AutenticacaoUser {
    private static final List<Usuario> usuarios = new ArrayList<>();


    public static boolean cadastrarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(usuario.getCpf())) {
                return false;
            }
        }
        usuarios.add(usuario);
        return true;
    }


    public static Usuario autenticar(String cpf, String senha) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }


    public static List<Usuario> listarUsuarios() {
        return usuarios;
    }

    static {
        usuarios.add(new Usuario("777", "ADM RAIZ", "adm@solar.com", "adm123", true));
    }
}
