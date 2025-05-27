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
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarPorUsuario(String cpfUsuario) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE cpf_usuario = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(construirCliente(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public Cliente buscarPorCpfCliente(String cpfUsuario, String cpfCliente) {
        String sql = "SELECT * FROM cliente WHERE cpf_usuario = ? AND cpf = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfUsuario);
            stmt.setString(2, cpfCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirCliente(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Cliente c, String cpfUsuario) {
        String sql = "UPDATE cliente SET nome=?, email=?, logradouro=?, numero=?, bairro=?, cidade=?, estado=?, cep=? WHERE cpf_usuario=? AND cpf=?";
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
            stmt.setString(9, cpfUsuario);
            stmt.setString(10, c.getCpf());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(String cpfUsuario, String cpfCliente) {
        String sql = "DELETE FROM cliente WHERE cpf_usuario=? AND cpf=?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfUsuario);
            stmt.setString(2, cpfCliente);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente construirCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(rs.getString("cpf"), rs.getString("nome"));
        c.setEmail(rs.getString("email"));
        c.setLogradouro(rs.getString("logradouro"));
        c.setNumero(rs.getString("numero"));
        c.setBairro(rs.getString("bairro"));
        c.setCidade(rs.getString("cidade"));
        c.setEstado(rs.getString("estado"));
        c.setCep(rs.getString("cep"));
        return c;
    }
}
