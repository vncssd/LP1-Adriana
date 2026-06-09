package org.example.dao.farmacia;

import org.example.db.ConnectionFactory;
import org.example.model.farmacia.FarmaceuticoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmaceuticoDAO {

    public FarmaceuticoModel salvar(FarmaceuticoModel f) {
        String sql = "INSERT INTO farmaceutico (farmacia_id, nome, crf, esta_de_servico) VALUES (?,?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getFarmaciaId());
            ps.setString(2, f.getNome());
            ps.setString(3, f.getCrf());
            ps.setBoolean(4, f.isEstaDeServico());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar farmacêutico", e); }
        return f;
    }

    public void atualizar(FarmaceuticoModel f) {
        String sql = "UPDATE farmaceutico SET nome=?, crf=?, esta_de_servico=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCrf());
            ps.setBoolean(3, f.isEstaDeServico());
            ps.setInt(4, f.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar farmacêutico", e); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM farmaceutico WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao deletar farmacêutico", e); }
    }

    public List<FarmaceuticoModel> listarPorFarmacia(int farmaciaId) {
        String sql = "SELECT * FROM farmaceutico WHERE farmacia_id=? ORDER BY nome";
        List<FarmaceuticoModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, farmaciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FarmaceuticoModel f = new FarmaceuticoModel();
                    f.setId(rs.getInt("id"));
                    f.setFarmaciaId(rs.getInt("farmacia_id"));
                    f.setNome(rs.getString("nome"));
                    f.setCrf(rs.getString("crf"));
                    f.setEstaDeServico(rs.getBoolean("esta_de_servico"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar farmacêuticos", e); }
        return lista;
    }
}
