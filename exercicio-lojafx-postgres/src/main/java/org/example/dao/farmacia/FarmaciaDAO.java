package org.example.dao.farmacia;

import org.example.db.ConnectionFactory;
import org.example.model.farmacia.FarmaciaModel;

import java.sql.*;

public class FarmaciaDAO {

    public FarmaciaModel salvar(FarmaciaModel farmacia) {
        String sql = "INSERT INTO farmacia (esta_aberta) VALUES (?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, farmacia.isEstaAberta());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) farmacia.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar farmácia", e); }
        return farmacia;
    }

    public void atualizar(FarmaciaModel farmacia) {
        String sql = "UPDATE farmacia SET esta_aberta=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, farmacia.isEstaAberta());
            ps.setInt(2, farmacia.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar farmácia", e); }
    }

    public FarmaciaModel buscarOuCriar() {
        String sql = "SELECT * FROM farmacia LIMIT 1";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                FarmaciaModel f = new FarmaciaModel();
                f.setId(rs.getInt("id"));
                f.setEstaAberta(rs.getBoolean("esta_aberta"));
                return f;
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao buscar farmácia", e); }
        FarmaciaModel nova = new FarmaciaModel();
        nova.setEstaAberta(false);
        return salvar(nova);
    }
}
