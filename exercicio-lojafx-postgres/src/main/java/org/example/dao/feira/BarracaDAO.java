package org.example.dao.feira;

import org.example.db.ConnectionFactory;
import org.example.model.feira.BarracaModel;

import java.sql.*;

public class BarracaDAO {

    // ------------------------------------------------------------------ INSERT
    public BarracaModel salvar(BarracaModel barraca) {
        String sql = """
                INSERT INTO barraca (esta_aberta, horas_de_expediente)
                VALUES (?, ?)
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, barraca.isEstaAberta());
            ps.setInt(2, barraca.getHorasDeExpediente());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) barraca.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar barraca", e);
        }
        return barraca;
    }

    // ------------------------------------------------------------------ UPDATE
    public void atualizar(BarracaModel barraca) {
        String sql = """
                UPDATE barraca
                   SET esta_aberta = ?, horas_de_expediente = ?
                 WHERE id = ?
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, barraca.isEstaAberta());
            ps.setInt(2, barraca.getHorasDeExpediente());
            ps.setInt(3, barraca.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar barraca", e);
        }
    }

    // ------------------------------------------------------------------ SELECT (sempre usamos a primeira/única barraca)
    public BarracaModel buscarOuCriar() {
        String sql = "SELECT * FROM barraca LIMIT 1";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                BarracaModel b = new BarracaModel();
                b.setId(rs.getInt("id"));
                b.setEstaAberta(rs.getBoolean("esta_aberta"));
                b.setHorasDeExpediente(rs.getInt("horas_de_expediente"));
                return b;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar barraca", e);
        }
        // Nenhuma barraca no banco — cria uma
        BarracaModel nova = new BarracaModel();
        nova.setEstaAberta(false);
        nova.setHorasDeExpediente(8);
        return salvar(nova);
    }
}
