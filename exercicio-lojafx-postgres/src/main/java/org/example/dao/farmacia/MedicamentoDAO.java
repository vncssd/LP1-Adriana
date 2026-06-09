package org.example.dao.farmacia;

import org.example.db.ConnectionFactory;
import org.example.enums.farmacia.TipoMedicamento;
import org.example.model.farmacia.MedicamentoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public MedicamentoModel salvar(MedicamentoModel m) {
        String sql = "INSERT INTO medicamento (farmacia_id, nome, preco, quantidade_em_estoque, quantidade_vendida, tipo) VALUES (?,?,?,?,?,?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getFarmaciaId());
            ps.setString(2, m.getNome());
            ps.setDouble(3, m.getPreco());
            ps.setInt(4, m.getQuantidadeEmEstoque());
            ps.setInt(5, m.getQuantidadeVendida());
            ps.setString(6, m.getTipo().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar medicamento", e); }
        return m;
    }

    public void atualizar(MedicamentoModel m) {
        String sql = "UPDATE medicamento SET nome=?, preco=?, quantidade_em_estoque=?, quantidade_vendida=?, tipo=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setDouble(2, m.getPreco());
            ps.setInt(3, m.getQuantidadeEmEstoque());
            ps.setInt(4, m.getQuantidadeVendida());
            ps.setString(5, m.getTipo().name());
            ps.setInt(6, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar medicamento", e); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM medicamento WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao deletar medicamento", e); }
    }

    public MedicamentoModel buscarPorNome(String nome, int farmaciaId) {
        String sql = "SELECT * FROM medicamento WHERE LOWER(nome)=LOWER(?) AND farmacia_id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, farmaciaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao buscar medicamento", e); }
        return null;
    }

    public List<MedicamentoModel> listarPorFarmacia(int farmaciaId) {
        String sql = "SELECT * FROM medicamento WHERE farmacia_id=? ORDER BY nome";
        List<MedicamentoModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, farmaciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar medicamentos", e); }
        return lista;
    }

    private MedicamentoModel mapear(ResultSet rs) throws SQLException {
        MedicamentoModel m = new MedicamentoModel();
        m.setId(rs.getInt("id"));
        m.setFarmaciaId(rs.getInt("farmacia_id"));
        m.setNome(rs.getString("nome"));
        m.setPreco(rs.getDouble("preco"));
        m.setQuantidadeEmEstoque(rs.getInt("quantidade_em_estoque"));
        m.setQuantidadeVendida(rs.getInt("quantidade_vendida"));
        m.setTipo(TipoMedicamento.valueOf(rs.getString("tipo")));
        return m;
    }
}
