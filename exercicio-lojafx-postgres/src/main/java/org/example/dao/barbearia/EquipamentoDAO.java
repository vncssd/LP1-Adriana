package org.example.dao.barbearia;

import org.example.db.ConnectionFactory;
import org.example.enums.barbearia.EquipamentoEstado;
import org.example.model.barbearia.EquipamentoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    // ------------------------------------------------------------------ INSERT
    public EquipamentoModel salvar(EquipamentoModel equipamento) {
        String sql = """
                INSERT INTO equipamento (barbearia_id, marca, modelo, recebeu_manutencao, estado)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, equipamento.getBarbeariaId());
            ps.setString(2, equipamento.getMarca());
            ps.setString(3, equipamento.getModelo());
            ps.setBoolean(4, equipamento.isRecebeuManuntencao());
            ps.setString(5, equipamento.getEquipamentoEstado().name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) equipamento.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar equipamento", e);
        }
        return equipamento;
    }

    // ------------------------------------------------------------------ UPDATE
    public void atualizar(EquipamentoModel equipamento) {
        String sql = """
                UPDATE equipamento
                   SET marca = ?, modelo = ?, recebeu_manutencao = ?, estado = ?
                 WHERE id = ?
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, equipamento.getMarca());
            ps.setString(2, equipamento.getModelo());
            ps.setBoolean(3, equipamento.isRecebeuManuntencao());
            ps.setString(4, equipamento.getEquipamentoEstado().name());
            ps.setInt(5, equipamento.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar equipamento", e);
        }
    }

    // ------------------------------------------------------------------ DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM equipamento WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar equipamento", e);
        }
    }

    // ------------------------------------------------------------------ SELECT por barbearia
    public List<EquipamentoModel> listarPorBarbearia(int barbeariaId) {
        String sql = "SELECT * FROM equipamento WHERE barbearia_id = ? ORDER BY marca, modelo";
        List<EquipamentoModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, barbeariaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar equipamentos", e);
        }
        return lista;
    }

    // ------------------------------------------------------------------ HELPER
    private EquipamentoModel mapear(ResultSet rs) throws SQLException {
        EquipamentoModel e = new EquipamentoModel();
        e.setId(rs.getInt("id"));
        e.setBarbeariaId(rs.getInt("barbearia_id"));
        e.setMarca(rs.getString("marca"));
        e.setModelo(rs.getString("modelo"));
        e.setRecebeuManuntencao(rs.getBoolean("recebeu_manutencao"));
        e.setEquipamentoEstado(EquipamentoEstado.valueOf(rs.getString("estado")));
        return e;
    }
}
