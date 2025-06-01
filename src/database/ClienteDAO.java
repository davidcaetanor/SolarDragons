package database;

import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean cadastrar(Cliente c, String cpfUsuario) {
        String sql = "INSERT INTO cliente (nome, email, cpf, logradouro, numero, bairro, cidade, estado, cep, cpf_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getCpf());
            stmt.setString(4, c.getLogradouro());
            stmt.setString(5, c.getNumero());
            stmt.setString(6, c.getBairro());
            stmt.setString(7, c.getCidade());
            stmt.setString(8, c.getEstado());
            stmt.setString(9, c.getCep());
            stmt.setString(10, cpfUsuario);
            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Cliente c, String cpfUsuario) {
        String sql = "UPDATE cliente SET nome=?, email=?, logradouro=?, numero=?, bairro=?, cidade=?, estado=?, cep=? WHERE cpf=? AND cpf_usuario=?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getLogradouro());
            stmt.setString(4, c.getNumero());
            stmt.setString(5, c.getBairro());
            stmt.setString(6, c.getCidade());
            stmt.setString(7, c.getEstado());
            stmt.setString(8, c.getCep());
            stmt.setString(9, c.getCpf());
            stmt.setString(10, cpfUsuario);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(String cpfUsuario, String cpfCliente) {
        String sql = "DELETE FROM cliente WHERE cpf = ? AND cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
            stmt.setString(2, cpfUsuario);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Cliente buscarPorCpfCliente(String cpfUsuario, String cpfCliente) {
        String sql = "SELECT * FROM cliente WHERE cpf = ? AND cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
            stmt.setString(2, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente(rs.getString("cpf"), rs.getString("nome"));
                    c.setId(rs.getInt("id"));
                    c.setEmail(rs.getString("email"));
                    c.setLogradouro(rs.getString("logradouro"));
                    c.setNumero(rs.getString("numero"));
                    c.setBairro(rs.getString("bairro"));
                    c.setCidade(rs.getString("cidade"));
                    c.setEstado(rs.getString("estado"));
                    c.setCep(rs.getString("cep"));
                    c.setCpfUsuario(rs.getString("cpf_usuario"));
                    return c;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Cliente> listarPorUsuario(String cpfUsuario) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente(rs.getString("cpf"), rs.getString("nome"));
                    c.setId(rs.getInt("id"));
                    c.setEmail(rs.getString("email"));
                    c.setLogradouro(rs.getString("logradouro"));
                    c.setNumero(rs.getString("numero"));
                    c.setBairro(rs.getString("bairro"));
                    c.setCidade(rs.getString("cidade"));
                    c.setEstado(rs.getString("estado"));
                    c.setCep(rs.getString("cep"));
                    c.setCpfUsuario(rs.getString("cpf_usuario"));
                    clientes.add(c);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientes;
    }

    public boolean emailExisteParaUsuario(String email, String cpfUsuario) {
        String sql = "SELECT id FROM cliente WHERE email = ? AND cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean cpfExisteParaUsuario(String cpfCliente, String cpfUsuario) {
        String sql = "SELECT id FROM cliente WHERE cpf = ? AND cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
            stmt.setString(2, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexaoMySQL.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(rs.getString("cpf"), rs.getString("nome"));
                c.setId(rs.getInt("id"));
                c.setEmail(rs.getString("email"));
                c.setLogradouro(rs.getString("logradouro"));
                c.setNumero(rs.getString("numero"));
                c.setBairro(rs.getString("bairro"));
                c.setCidade(rs.getString("cidade"));
                c.setEstado(rs.getString("estado"));
                c.setCep(rs.getString("cep"));
                c.setCpfUsuario(rs.getString("cpf_usuario"));
                clientes.add(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientes;
    }
}
