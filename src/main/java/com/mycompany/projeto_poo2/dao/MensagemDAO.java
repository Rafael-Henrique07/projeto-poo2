package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Mensagem;
import com.mycompany.projeto_poo2.model.Usuario;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MensagemDAO {

    private static final RowMapper<Mensagem> MAPEADOR = (rs, linha) -> new Mensagem(
            rs.getInt("id"),
            Usuario.resumo(rs.getInt("remetente_id"), rs.getString("remetente_nome")),
            rs.getString("conteudo"),
            rs.getObject("data_envio", LocalDateTime.class),
            rs.getBoolean("lida"));

    private final JdbcTemplate jdbc;

    public MensagemDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void inserir(Integer negociacaoId, Integer remetenteId, String conteudo) {
        jdbc.update("INSERT INTO mensagem (negociacao_id, remetente_id, conteudo, data_envio, lida) "
                + "VALUES (?, ?, ?, ?, FALSE)",
                negociacaoId, remetenteId, conteudo, Timestamp.valueOf(LocalDateTime.now()));
    }

    public List<Mensagem> listarPorNegociacao(Integer negociacaoId) {
        return jdbc.query("SELECT m.*, u.nome AS remetente_nome FROM mensagem m JOIN usuario u ON u.id = m.remetente_id "
                + "WHERE m.negociacao_id = ? ORDER BY m.id", MAPEADOR, negociacaoId);
    }

    public void marcarComoLidas(Integer negociacaoId, Integer leitorId) {
        jdbc.update("UPDATE mensagem SET lida = TRUE WHERE negociacao_id = ? AND remetente_id <> ? AND lida = FALSE",
                negociacaoId, leitorId);
    }

    public int contarNaoLidas(Integer usuarioId) {
        Integer total = jdbc.queryForObject("""
                SELECT COUNT(*) FROM mensagem m
                JOIN negociacao n ON n.id = m.negociacao_id
                JOIN anuncio a ON a.id = n.anuncio_id
                WHERE m.lida = FALSE AND m.remetente_id <> ? AND (n.comprador_id = ? OR a.anunciante_id = ?)
                """, Integer.class, usuarioId, usuarioId, usuarioId);
        return total == null ? 0 : total;
    }
}
