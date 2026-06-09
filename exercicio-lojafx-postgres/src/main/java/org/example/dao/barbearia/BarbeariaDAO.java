package org.example.dao.barbearia;

import org.example.db.ConnectionFactory;
import org.example.model.barbearia.BarbeariaModel;

import java.sql.*;

public class BarbeariaDAO {

    // ------------------------------------------------------------------ INSERT
    public BarbeariaModel salvar(BarbeariaModel barbearia) {
        String sql = """
                INSERT INTO barbearia (esta_aberta, possui_equipamentos_sem_manutencao)
                VALUES (?, ?)
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, barbearia.isEstaAberto());
            ps.setBoolean(2, barbearia.possuiEquipamentosSemManutencao());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) barbearia.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar barbearia", e);
        }
        return barbearia;
    }

    // ------------------------------------------------------------------ UPDATE
    public void atualizar(BarbeariaModel barbearia) {
        String sql = """
                UPDATE barbearia
                   SET esta_aberta = ?, possui_equipamentos_sem_manutencao = ?
                 WHERE id = ?
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, barbearia.isEstaAberto());
            ps.setBoolean(2, barbearia.possuiEquipamentosSemManutencao());
            ps.setInt(3, barbearia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar barbearia", e);
        }
    }

    // ------------------------------------------------------------------ SELECT (única barbearia)
    public BarbeariaModel buscarOuCriar() {
        String sql = "SELECT * FROM barbearia LIMIT 1";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                BarbeariaModel b = new BarbeariaModel();
                b.setId(rs.getInt("id"));
                b.setEstaAberto(rs.getBoolean("esta_aberta"));
                b.setpossuiEquipamentosSemManutencao(rs.getBoolean("possui_equipamentos_sem_manutencao"));
                return b;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar barbearia", e);
        }
        BarbeariaModel nova = new BarbeariaModel();
        nova.setEstaAberto(false);
        nova.setpossuiEquipamentosSemManutencao(false);
        return salvar(nova);
    }
}
