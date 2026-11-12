package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.dto.ResumoNegociacao;
import com.mycompany.projeto_poo2.model.StatusNegociacao;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class NegociacaoDAO {

    public record Registro(Integer id, Integer anuncioId, Integer compradorId, LocalDateTime dataInicio,
            StatusNegociacao status) {

        public boolean isAberta() {
            return status == StatusNegociacao.ABERTA;
        }
    }

    private static final RowMapper<Registro> MAPEADOR = (rs, linha) -> new Registro(
            rs.getInt("id"), rs.getInt("anuncio_id"), rs.getInt("comprador_id"),
            rs.getObject("data_inicio", LocalDateTime.class), StatusNegociacao.valueOf(rs.getString("status")));

    private static final RowMapper<ResumoNegociacao> MAPEADOR_RESUMO = (rs, linha) -> new ResumoNegociacao(
            rs.getInt("id"), rs.getString("titulo"), rs.getString("outra_parte"),
            rs.getObject("data_inicio", LocalDateTime.class), StatusNegociacao.valueOf(rs.getString("status")));

    private final JdbcTemplate jdbc;

    public NegociacaoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Integer inserir(Integer anuncioId, Integer compradorId) {
        String sql = "INSERT INTO negociacao (anuncio_id, comprador_id, data_inicio, status) VALUES (?, ?, ?, ?)";
        KeyHolder chave = new GeneratedKeyHolder();
        jdbc.update(conexao -> {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setInt(1, anuncioId);
            comando.setInt(2, compradorId);
            comando.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            comando.setString(4, StatusNegociacao.ABERTA.name());
            return comando;
        }, chave);
        return chave.getKey().intValue();
    }

    public Optional<Registro> buscarPorId(Integer id) {
        return jdbc.query("SELECT * FROM negociacao WHERE id = ?", MAPEADOR, id).stream().findFirst();
    }

    public Optional<Registro> buscarPorAnuncioEComprador(Integer anuncioId, Integer compradorId) {
        return jdbc.query("SELECT * FROM negociacao WHERE anuncio_id = ? AND comprador_id = ?", MAPEADOR, anuncioId,
                compradorId).stream().findFirst();
    }

    public void reabrir(Integer id) {
        jdbc.update("UPDATE negociacao SET status = ? WHERE id = ?", StatusNegociacao.ABERTA.name(), id);
    }

    public void encerrarPorAnuncio(Integer anuncioId) {
        jdbc.update("UPDATE negociacao SET status = ? WHERE anuncio_id = ?", StatusNegociacao.ENCERRADA.name(),
                anuncioId);
    }

    public List<ResumoNegociacao> listarComoComprador(Integer usuarioId) {
        return jdbc.query("SELECT n.id, n.data_inicio, n.status, a.titulo, u.nome AS outra_parte "
                + "FROM negociacao n JOIN anuncio a ON a.id = n.anuncio_id JOIN usuario u ON u.id = a.anunciante_id "
                + "WHERE n.comprador_id = ? ORDER BY n.id DESC", MAPEADOR_RESUMO, usuarioId);
    }

    public List<ResumoNegociacao> listarComoAnunciante(Integer usuarioId) {
        return jdbc.query("SELECT n.id, n.data_inicio, n.status, a.titulo, u.nome AS outra_parte "
                + "FROM negociacao n JOIN anuncio a ON a.id = n.anuncio_id JOIN usuario u ON u.id = n.comprador_id "
                + "WHERE a.anunciante_id = ? ORDER BY n.id DESC", MAPEADOR_RESUMO, usuarioId);
    }
}
