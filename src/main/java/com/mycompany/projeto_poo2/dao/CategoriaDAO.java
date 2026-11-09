package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaDAO {

    private static final RowMapper<Categoria> MAPEADOR = (rs, linha) -> new Categoria(
            rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));

    private final JdbcTemplate jdbc;

    public CategoriaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Categoria> listar() {
        return jdbc.query("SELECT * FROM categoria ORDER BY nome", MAPEADOR);
    }

    public Optional<Categoria> buscarPorId(Integer id) {
        return jdbc.query("SELECT * FROM categoria WHERE id = ?", MAPEADOR, id).stream().findFirst();
    }

    public boolean nomeEmUso(String nome, Integer ignorarId) {
        Integer total = jdbc.queryForObject("SELECT COUNT(*) FROM categoria WHERE nome = ? AND id <> ?",
                Integer.class, nome, ignorarId == null ? 0 : ignorarId);
        return total != null && total > 0;
    }

    public boolean possuiVeiculos(Integer id) {
        Integer total = jdbc.queryForObject("SELECT COUNT(*) FROM veiculo WHERE categoria_id = ?", Integer.class, id);
        return total != null && total > 0;
    }

    public void inserir(String nome, String descricao) {
        jdbc.update("INSERT INTO categoria (nome, descricao) VALUES (?, ?)", nome, descricao);
    }

    public void atualizar(Integer id, String nome, String descricao) {
        jdbc.update("UPDATE categoria SET nome = ?, descricao = ? WHERE id = ?", nome, descricao, id);
    }

    public void excluir(Integer id) {
        jdbc.update("DELETE FROM categoria WHERE id = ?", id);
    }
}
