package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.PerfilUsuario;
import com.mycompany.projeto_poo2.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO {

    private static final RowMapper<Usuario> MAPEADOR = (rs, linha) -> new Usuario(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("senha_hash"),
            rs.getString("telefone"),
            rs.getString("cidade"),
            rs.getString("uf"),
            PerfilUsuario.valueOf(rs.getString("perfil")),
            rs.getObject("data_cadastro", LocalDateTime.class));

    private final JdbcTemplate jdbc;

    public UsuarioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Integer inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha_hash, telefone, cidade, uf, perfil, data_cadastro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder chave = new GeneratedKeyHolder();
        jdbc.update(conexao -> {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenhaHash());
            comando.setString(4, usuario.getTelefone());
            comando.setString(5, usuario.getCidade());
            comando.setString(6, usuario.getUf());
            comando.setString(7, usuario.getPerfil().name());
            comando.setTimestamp(8, Timestamp.valueOf(usuario.getDataCadastro()));
            return comando;
        }, chave);
        return chave.getKey().intValue();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return jdbc.query("SELECT * FROM usuario WHERE id = ?", MAPEADOR, id).stream().findFirst();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return jdbc.query("SELECT * FROM usuario WHERE email = ?", MAPEADOR, email).stream().findFirst();
    }

    public boolean emailEmUso(String email, Integer ignorarId) {
        Integer total = jdbc.queryForObject("SELECT COUNT(*) FROM usuario WHERE email = ? AND id <> ?",
                Integer.class, email, ignorarId == null ? 0 : ignorarId);
        return total != null && total > 0;
    }

    public void atualizarDados(Integer id, String nome, String email, String telefone, String cidade, String uf) {
        jdbc.update("UPDATE usuario SET nome = ?, email = ?, telefone = ?, cidade = ?, uf = ? WHERE id = ?",
                nome, email, telefone, cidade, uf, id);
    }

    public void atualizarSenha(Integer id, String senhaHash) {
        jdbc.update("UPDATE usuario SET senha_hash = ? WHERE id = ?", senhaHash, id);
    }
}
