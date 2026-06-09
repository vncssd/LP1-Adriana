package org.example.dao.loja;

import org.example.db.ConnectionFactory;
import org.example.model.loja.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // ------------------------------------------------------------------ INSERT
    public ProdutoModel salvar(ProdutoModel produto) {
        String sql = """
                INSERT INTO produto (nome, preco, quantidade_em_estoque, quantidade_vendida)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidadeEmEstoque());
            ps.setInt(4, produto.getQuantidadeVendida());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) produto.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
        return produto;
    }

    // ------------------------------------------------------------------ UPDATE
    public void atualizar(ProdutoModel produto) {
        String sql = """
                UPDATE produto
                   SET nome = ?, preco = ?,
                       quantidade_em_estoque = ?, quantidade_vendida = ?
                 WHERE id = ?
                """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidadeEmEstoque());
            ps.setInt(4, produto.getQuantidadeVendida());
            ps.setInt(5, produto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    // ------------------------------------------------------------------ DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto", e);
        }
    }

    // ------------------------------------------------------------------ SELECT por ID
    public ProdutoModel buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por id", e);
        }
        return null;
    }

    // ------------------------------------------------------------------ SELECT por Nome
    public ProdutoModel buscarPorNome(String nome) {
        String sql = "SELECT * FROM produto WHERE LOWER(nome) = LOWER(?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por nome", e);
        }
        return null;
    }

    // ------------------------------------------------------------------ SELECT ALL
    public List<ProdutoModel> listarTodos() {
        String sql = "SELECT * FROM produto ORDER BY nome";
        List<ProdutoModel> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return lista;
    }

    // ------------------------------------------------------------------ HELPER
    private ProdutoModel mapear(ResultSet rs) throws SQLException {
        ProdutoModel p = new ProdutoModel();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getDouble("preco"));
        p.setQuantidadeEmEstoque(rs.getInt("quantidade_em_estoque"));
        p.setQuantidadeVendida(rs.getInt("quantidade_vendida"));
        return p;
    }
}
