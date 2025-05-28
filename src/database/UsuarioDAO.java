package database;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean cadastrar(Usuario u) {
        String sql = "INSERT INTO usuario (nome, email, cpf, senha, administrador, raiz) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getCpf());
            stmt.setString(4, u.getSenha());
            stmt.setBoolean(5, u.isAdmin());
            stmt.setBoolean(6, u.isRootAdmin());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM usuario WHERE cpf = ? AND senha = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
        }
        return null;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = ConexaoMySQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirUsuario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Usuario u) {
        String sql = "UPDATE usuario SET nome=?, email=?, senha=?, administrador=?, raiz=? WHERE cpf=?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setBoolean(4, u.isAdmin());
            stmt.setBoolean(5, u.isRootAdmin());
            stmt.setString(6, u.getCpf());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(String cpf) {
        String sql = "DELETE FROM usuario WHERE cpf = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
            return false;
        }
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("cpf"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getBoolean("administrador"),
                rs.getBoolean("raiz")
        );
    }

    public boolean cpfExiste(String cpf) {
        String sql = "SELECT 1 FROM usuario WHERE cpf = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar email: " + e.getMessage());
            return false;
        }
    }

    public Usuario buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por CPF: " + e.getMessage());
        }
        return null;
    }

}
