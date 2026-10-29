package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, telefone, comprador, anunciante) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql);

        comando.setString(1, usuario.getNome());
        comando.setString(2, usuario.getEmail());
        comando.setString(3, usuario.getSenha());
        comando.setString(4, usuario.getTelefone());
        comando.setBoolean(5, usuario.isComprador());
        comando.setBoolean(6, usuario.isAnunciante());

        comando.executeUpdate();

        comando.close();
        conexao.close();
    }
}