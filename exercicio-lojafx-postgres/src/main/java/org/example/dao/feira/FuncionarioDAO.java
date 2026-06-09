package org.example.dao.feira;

import org.example.db.ConnectionFactory;
import org.example.model.feira.FuncionarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // ------------------------------------------------------------------ INSERT
    public FuncionarioModel salvar(FuncionarioModel funcionario) {
        String sql = """
                INSERT INTO funcionario (barraca_id, nome, salario_por_hora, horas_trabalhadas_semana)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, funcionario.getBarracaId());
            ps.setString(2, funcionario.getNome());
            ps.setDouble(3, funcionario.getSalarioPorHora());
            ps.setInt(4, funcionario.getHorasTrabalhadasSemana());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) funcionario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário", e);
        }
        return funcionario;
    }

    // ------------------------------------------------------------------ UPDATE (horas trabalhadas)
    public void atualizarHoras(FuncionarioModel funcionario) {
        String sql = "UPDATE funcionario SET horas_trabalhadas_semana = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, funcionario.getHorasTrabalhadasSemana());
            ps.setInt(2, funcionario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar horas do funcionário", e);
        }
    }

    // ------------------------------------------------------------------ UPDATE completo
    public void atualizar(FuncionarioModel funcionario) {
        String sql = """
                UPDATE funcionario
                   SET nome = ?, salario_por_hora = ?, horas_trabalhadas_semana = ?
                 WHERE id = ?
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, funcionario.getNome());
            ps.setDouble(2, funcionario.getSalarioPorHora());
            ps.setInt(3, funcionario.getHorasTrabalhadasSemana());
            ps.setInt(4, funcionario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }

    // ------------------------------------------------------------------ DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário", e);
        }
    }

    // ------------------------------------------------------------------ SELECT por barraca
    public List<FuncionarioModel> listarPorBarraca(int barracaId) {
        String sql = "SELECT * FROM funcionario WHERE barraca_id = ? ORDER BY nome";
        List<FuncionarioModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, barracaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários", e);
        }
        return lista;
    }

    // ------------------------------------------------------------------ HELPER
    private FuncionarioModel mapear(ResultSet rs) throws SQLException {
        FuncionarioModel f = new FuncionarioModel();
        f.setId(rs.getInt("id"));
        f.setBarracaId(rs.getInt("barraca_id"));
        f.setNome(rs.getString("nome"));
        f.setSalarioPorHora(rs.getDouble("salario_por_hora"));
        f.setHorasTrabalhadasSemana(rs.getInt("horas_trabalhadas_semana"));
        return f;
    }
}
