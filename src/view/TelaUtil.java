package view;

import javax.swing.*;
import service.SessaoUsuario;

public class TelaUtil {

    public static void voltarParaPainelUsuario(JFrame telaAtual) {
        telaAtual.dispose();
        if (SessaoUsuario.getUsuarioLogado().isAdmin()) {
            new TelaADM();
        } else {
            new TelaPrincipalUsuario();
        }
    }
}
