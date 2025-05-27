package database;

import model.SimulacaoEnergia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimulacaoEnergiaDAO {

    public boolean cadastrar(SimulacaoEnergia s, int clienteId) {
        String sql = "INSERT INTO simulacao_energia (cliente_id, valor_conta_reais, tarifa, consumo_estimado_kwh, geracao_estimada_kwh, economia_mensal, economia_anual, potencia_sistema_kw, quantidade_modulos, investimento_total, tempo_payback_meses) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setDouble(2, s.getValorContaReais());
            stmt.setDouble(3, s.getTarifa());
            stmt.setDouble(4, s.getConsumoEstimadoKwh());
            stmt.setDouble(5, s.getGeracaoEstimadaKwh());
            stmt.setDouble(6, s.getEconomiaMensal());
            stmt.setDouble(7, s.getEconomiaAnual());
            stmt.setDouble(8, s.getPotenciaSistemaKw());
            stmt.setInt(9, s.getQuantidadeModulos());
            stmt.setDouble(10, s.getCustoSistema());
            stmt.setInt(11, (int)Math.round(s.getPaybackAnos() * 12)); // Em meses
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar simulação: " + e.getMessage());
            return false;
        }
    }

    public List<SimulacaoEnergia> listarPorCliente(int clienteId) {
        List<SimulacaoEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM simulacao_energia WHERE cliente_id = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(construirSimulacao(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar simulações: " + e.getMessage());
        }
        return lista;
    }

    private SimulacaoEnergia construirSimulacao(ResultSet rs) throws SQLException {
        return new SimulacaoEnergia(
                "",
                rs.getDouble("valor_conta_reais")
        );

    }
}
