package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Foto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FotoDAO {

    private static final RowMapper<Foto> MAPEADOR = (rs, linha) -> new Foto(
            rs.getInt("id"), rs.getString("caminho_arquivo"), rs.getInt("ordem"), rs.getBoolean("principal"));

    private final JdbcTemplate jdbc;

    public FotoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void inserir(Integer anuncioId, String caminhoArquivo, int ordem, boolean principal) {
        jdbc.update("INSERT INTO foto (anuncio_id, caminho_arquivo, ordem, principal) VALUES (?, ?, ?, ?)",
                anuncioId, caminhoArquivo, ordem, principal);
    }

    public List<Foto> listarPorAnuncio(Integer anuncioId) {
        return jdbc.query("SELECT * FROM foto WHERE anuncio_id = ? ORDER BY ordem, id", MAPEADOR, anuncioId);
    }

    public void definirPrincipal(Integer anuncioId, Integer fotoId) {
        jdbc.update("UPDATE foto SET principal = (id = ?) WHERE anuncio_id = ?", fotoId, anuncioId);
    }

    public void atualizarOrdem(Integer fotoId, int ordem) {
        jdbc.update("UPDATE foto SET ordem = ? WHERE id = ?", ordem, fotoId);
    }

    public void remover(Integer fotoId) {
        jdbc.update("DELETE FROM foto WHERE id = ?", fotoId);
    }
}
