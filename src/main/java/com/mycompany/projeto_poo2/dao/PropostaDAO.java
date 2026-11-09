package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Proposta;
import com.mycompany.projeto_poo2.model.StatusProposta;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PropostaDAO {

    private static final RowMapper<Proposta> MAPEADOR = (rs, linha) -> new Proposta(
            rs.getInt("id"), rs.getBigDecimal("valor"), rs.getObject("data_proposta", LocalDateTime.class),
            StatusProposta.valueOf(rs.getString("status")));

    private final JdbcTemplate jdbc;

    public PropostaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void inserir(Integer negociacaoId, BigDecimal valor) {
        jdbc.update("INSERT INTO proposta (negociacao_id, valor, data_proposta, status) VALUES (?, ?, ?, ?)",
                negociacaoId, valor, Timestamp.valueOf(LocalDateTime.now()), StatusProposta.PENDENTE.name());
    }

    public List<Proposta> listarPorNegociacao(Integer negociacaoId) {
        return jdbc.query("SELECT * FROM proposta WHERE negociacao_id = ? ORDER BY id DESC", MAPEADOR, negociacaoId);
    }

    public Optional<Proposta> buscar(Integer id, Integer negociacaoId) {
        return jdbc.query("SELECT * FROM proposta WHERE id = ? AND negociacao_id = ?", MAPEADOR, id, negociacaoId)
                .stream().findFirst();
    }

    public void cancelarPendentes(Integer negociacaoId) {
        jdbc.update("UPDATE proposta SET status = ? WHERE negociacao_id = ? AND status = ?",
                StatusProposta.CANCELADA.name(), negociacaoId, StatusProposta.PENDENTE.name());
    }

    public void cancelarPendentesPorAnuncio(Integer anuncioId) {
        jdbc.update("""
                UPDATE proposta p JOIN negociacao n ON n.id = p.negociacao_id
                SET p.status = ? WHERE n.anuncio_id = ? AND p.status = ?
                """, StatusProposta.CANCELADA.name(), anuncioId, StatusProposta.PENDENTE.name());
    }

    public boolean responder(Integer id, StatusProposta novoStatus) {
        return jdbc.update("UPDATE proposta SET status = ? WHERE id = ? AND status = ?", novoStatus.name(), id,
                StatusProposta.PENDENTE.name()) == 1;
    }
}
