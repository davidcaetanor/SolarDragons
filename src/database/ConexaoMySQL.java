package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {


    private static final String URL = "jdbc:mysql://localhost:3306/solardragons?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root"; // Seu USER do MYSQL
    private static final String SENHA = "David17012002@";   // Senha do seu MYSQL

    private static Connection conexao;

    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        return conexao;
    }

    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
