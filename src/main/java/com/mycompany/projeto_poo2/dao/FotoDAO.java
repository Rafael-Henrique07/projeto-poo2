package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Foto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FotoDAO {

    private static final RowMapper<Foto> MAPEADOR = (rs, linha) -> new Foto(
            rs.getInt("id"), rs.getString("caminho_arquivo"));

    private final JdbcTemplate jdbc;

    public FotoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void inserir(Integer anuncioId, String caminhoArquivo) {
        jdbc.update("INSERT INTO foto (anuncio_id, caminho_arquivo) VALUES (?, ?)", anuncioId, caminhoArquivo);
    }

    public List<Foto> listarPorAnuncio(Integer anuncioId) {
        return jdbc.query("SELECT * FROM foto WHERE anuncio_id = ? ORDER BY id", MAPEADOR, anuncioId);
    }

    public void remover(Integer fotoId) {
        jdbc.update("DELETE FROM foto WHERE id = ?", fotoId);
    }
}
