package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.dto.FiltroAnuncio;
import com.mycompany.projeto_poo2.model.Anuncio;
import com.mycompany.projeto_poo2.model.Categoria;
import com.mycompany.projeto_poo2.model.EstadoConservacao;
import com.mycompany.projeto_poo2.model.Foto;
import com.mycompany.projeto_poo2.model.PerfilUsuario;
import com.mycompany.projeto_poo2.model.StatusAnuncio;
import com.mycompany.projeto_poo2.model.TipoCambio;
import com.mycompany.projeto_poo2.model.TipoCombustivel;
import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.model.Veiculo;
import com.mycompany.projeto_poo2.selo.TipoSelo;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class AnuncioDAO {

    private static final String FROM = """
             FROM anuncio a
             JOIN veiculo v ON v.id = a.veiculo_id
             JOIN categoria c ON c.id = v.categoria_id
             JOIN usuario u ON u.id = a.anunciante_id
            """;

    private static final String SELECT = """
            SELECT a.id, a.titulo, a.descricao, a.preco, a.status, a.selo, a.data_publicacao, a.data_atualizacao,
                   v.id AS v_id, v.marca, v.modelo, v.ano_fabricacao, v.ano_modelo, v.quilometragem, v.cor, v.motor,
                   v.potencia_cv, v.combustivel, v.cambio, v.conservacao,
                   c.id AS c_id, c.nome AS c_nome, c.descricao AS c_descricao,
                   u.id AS u_id, u.nome AS u_nome, u.email AS u_email, u.telefone AS u_telefone,
                   u.cidade AS u_cidade, u.uf AS u_uf, u.perfil AS u_perfil,
                   (SELECT f.caminho_arquivo FROM foto f WHERE f.anuncio_id = a.id ORDER BY f.id LIMIT 1) AS capa
            """ + FROM;

    private static final RowMapper<Anuncio> MAPEADOR = (rs, linha) -> {
        Categoria categoria = new Categoria(rs.getInt("c_id"), rs.getString("c_nome"), rs.getString("c_descricao"));
        Veiculo veiculo = new Veiculo(rs.getInt("v_id"), categoria, rs.getString("marca"), rs.getString("modelo"),
                rs.getInt("ano_fabricacao"), rs.getInt("ano_modelo"), rs.getInt("quilometragem"), rs.getString("cor"),
                rs.getString("motor"), rs.getInt("potencia_cv"), TipoCombustivel.valueOf(rs.getString("combustivel")),
                TipoCambio.valueOf(rs.getString("cambio")), EstadoConservacao.valueOf(rs.getString("conservacao")));
        Usuario anunciante = new Usuario(rs.getInt("u_id"), rs.getString("u_nome"), rs.getString("u_email"), null,
                rs.getString("u_telefone"), rs.getString("u_cidade"), rs.getString("u_uf"),
                PerfilUsuario.valueOf(rs.getString("u_perfil")), null);
        String selo = rs.getString("selo");
        Anuncio anuncio = new Anuncio(rs.getInt("id"), anunciante, veiculo, rs.getString("titulo"),
                rs.getString("descricao"), rs.getBigDecimal("preco"), StatusAnuncio.valueOf(rs.getString("status")),
                selo == null ? null : TipoSelo.valueOf(selo), rs.getObject("data_publicacao", LocalDateTime.class),
                rs.getObject("data_atualizacao", LocalDateTime.class));
        String capa = rs.getString("capa");
        if (capa != null) {
            anuncio.setFotos(List.of(new Foto(null, capa)));
        }
        return anuncio;
    };

    private final JdbcTemplate jdbc;

    public AnuncioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Integer inserir(Integer anuncianteId, Integer veiculoId, String titulo, String descricao, BigDecimal preco,
            TipoSelo selo) {
        String sql = "INSERT INTO anuncio (anunciante_id, veiculo_id, titulo, descricao, preco, status, selo, "
                + "data_publicacao, data_atualizacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        KeyHolder chave = new GeneratedKeyHolder();
        jdbc.update(conexao -> {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setInt(1, anuncianteId);
            comando.setInt(2, veiculoId);
            comando.setString(3, titulo);
            comando.setString(4, descricao);
            comando.setBigDecimal(5, preco);
            comando.setString(6, StatusAnuncio.ATIVO.name());
            comando.setString(7, selo == null ? null : selo.name());
            comando.setTimestamp(8, agora);
            comando.setTimestamp(9, agora);
            return comando;
        }, chave);
        return chave.getKey().intValue();
    }

    public void atualizar(Integer id, String titulo, String descricao, BigDecimal preco, TipoSelo selo) {
        jdbc.update("UPDATE anuncio SET titulo = ?, descricao = ?, preco = ?, selo = ?, data_atualizacao = ? "
                + "WHERE id = ?",
                titulo, descricao, preco, selo == null ? null : selo.name(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    public void atualizarStatus(Integer id, StatusAnuncio status) {
        jdbc.update("UPDATE anuncio SET status = ?, data_atualizacao = ? WHERE id = ?",
                status.name(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    public Optional<Anuncio> buscarPorId(Integer id) {
        return jdbc.query(SELECT + " WHERE a.id = ?", MAPEADOR, id).stream().findFirst();
    }

    public List<Anuncio> listarPorAnunciante(Integer anuncianteId) {
        return jdbc.query(SELECT + " WHERE a.anunciante_id = ? AND a.status <> ? ORDER BY a.id DESC", MAPEADOR,
                anuncianteId, StatusAnuncio.REMOVIDO.name());
    }

    public List<Anuncio> pesquisar(FiltroAnuncio filtro) {
        List<Object> parametros = new ArrayList<>();
        String where = montarWhere(filtro, parametros);
        return jdbc.query(SELECT + where + " ORDER BY a.data_publicacao DESC, a.id DESC", MAPEADOR,
                parametros.toArray());
    }

    private String montarWhere(FiltroAnuncio f, List<Object> parametros) {
        StringBuilder where = new StringBuilder(" WHERE a.status = ?");
        parametros.add(StatusAnuncio.ATIVO.name());
        if (StringUtils.hasText(f.getBusca())) {
            String termo = "%" + f.getBusca().trim() + "%";
            where.append(" AND (v.marca LIKE ? OR v.modelo LIKE ? OR a.titulo LIKE ?)");
            parametros.add(termo);
            parametros.add(termo);
            parametros.add(termo);
        }
        if (f.getCategoriaId() != null) {
            where.append(" AND c.id = ?");
            parametros.add(f.getCategoriaId());
        }
        if (StringUtils.hasText(f.getMarca())) {
            where.append(" AND v.marca LIKE ?");
            parametros.add("%" + f.getMarca().trim() + "%");
        }
        if (f.getAnoMin() != null) {
            where.append(" AND v.ano_modelo >= ?");
            parametros.add(f.getAnoMin());
        }
        if (f.getAnoMax() != null) {
            where.append(" AND v.ano_modelo <= ?");
            parametros.add(f.getAnoMax());
        }
        if (f.getPrecoMin() != null) {
            where.append(" AND a.preco >= ?");
            parametros.add(f.getPrecoMin());
        }
        if (f.getPrecoMax() != null) {
            where.append(" AND a.preco <= ?");
            parametros.add(f.getPrecoMax());
        }
        if (f.getConservacao() != null) {
            where.append(" AND v.conservacao = ?");
            parametros.add(f.getConservacao().name());
        }
        if (StringUtils.hasText(f.getCidade())) {
            where.append(" AND u.cidade LIKE ?");
            parametros.add("%" + f.getCidade().trim() + "%");
        }
        if (StringUtils.hasText(f.getUf())) {
            where.append(" AND u.uf = ?");
            parametros.add(f.getUf());
        }
        if (f.getSelo() != null) {
            where.append(" AND a.selo = ?");
            parametros.add(f.getSelo().name());
        }
        return where.toString();
    }
}
