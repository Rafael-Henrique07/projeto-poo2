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
            rs.getObject("data_envio", LocalDateTime.class));

    private final JdbcTemplate jdbc;

    public MensagemDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void inserir(Integer negociacaoId, Integer remetenteId, String conteudo) {
        jdbc.update("INSERT INTO mensagem (negociacao_id, remetente_id, conteudo, data_envio) VALUES (?, ?, ?, ?)",
                negociacaoId, remetenteId, conteudo, Timestamp.valueOf(LocalDateTime.now()));
    }

    public List<Mensagem> listarPorNegociacao(Integer negociacaoId) {
        return jdbc.query("SELECT m.*, u.nome AS remetente_nome FROM mensagem m JOIN usuario u ON u.id = m.remetente_id "
                + "WHERE m.negociacao_id = ? ORDER BY m.id", MAPEADOR, negociacaoId);
    }
}
