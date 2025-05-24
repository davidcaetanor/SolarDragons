package service;

import model.Usuario;

public class SessaoUsuario {
    private static Usuario usuarioLogado;


    public static void login(Usuario usuario) {
        usuarioLogado = usuario;
    }


    public static void logout() {
        usuarioLogado = null;
    }


    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }


    public static boolean isLogado() {
        return usuarioLogado != null;
    }


    public static boolean isADM() {
        return usuarioLogado != null && usuarioLogado.isAdmin();
    }
}
