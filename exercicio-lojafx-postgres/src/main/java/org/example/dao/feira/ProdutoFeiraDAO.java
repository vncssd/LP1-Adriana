package org.example.dao.feira;

import org.example.db.ConnectionFactory;
import org.example.model.feira.ProdutoFeiraModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoFeiraDAO {

    public ProdutoFeiraModel salvar(ProdutoFeiraModel p) {
        String sql = "INSERT INTO produto_feira (barraca_id, nome, preco, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getBarracaId());
            ps.setString(2, p.getNome());
            ps.setDouble(3, p.getPreco());
            ps.setInt(4, p.getQuantidade());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar produto da feira", e); }
        return p;
    }

    public void atualizar(ProdutoFeiraModel p) {
        String sql = "UPDATE produto_feira SET nome=?, preco=?, quantidade=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPreco());
            ps.setInt(3, p.getQuantidade());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar produto da feira", e); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto_feira WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao deletar produto da feira", e); }
    }

    public List<ProdutoFeiraModel> listarPorBarraca(int barracaId) {
        String sql = "SELECT * FROM produto_feira WHERE barraca_id=? ORDER BY nome";
        List<ProdutoFeiraModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, barracaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdutoFeiraModel p = new ProdutoFeiraModel();
                    p.setId(rs.getInt("id"));
                    p.setBarracaId(rs.getInt("barraca_id"));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getDouble("preco"));
                    p.setQuantidade(rs.getInt("quantidade"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar produtos da feira", e); }
        return lista;
    }
}
