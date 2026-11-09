package com.mycompany.projeto_poo2.dao;

import com.mycompany.projeto_poo2.model.Veiculo;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class VeiculoDAO {

    private final JdbcTemplate jdbc;

    public VeiculoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Integer inserir(Veiculo v) {
        String sql = "INSERT INTO veiculo (categoria_id, marca, modelo, ano_fabricacao, ano_modelo, quilometragem, "
                + "cor, motor, potencia_cv, combustivel, cambio, conservacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder chave = new GeneratedKeyHolder();
        jdbc.update(conexao -> {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setInt(1, v.getCategoria().getId());
            comando.setString(2, v.getMarca());
            comando.setString(3, v.getModelo());
            comando.setInt(4, v.getAnoFabricacao());
            comando.setInt(5, v.getAnoModelo());
            comando.setInt(6, v.getQuilometragem());
            comando.setString(7, v.getCor());
            comando.setString(8, v.getMotor());
            comando.setInt(9, v.getPotenciaCv());
            comando.setString(10, v.getCombustivel().name());
            comando.setString(11, v.getCambio().name());
            comando.setString(12, v.getConservacao().name());
            return comando;
        }, chave);
        return chave.getKey().intValue();
    }

    public void atualizar(Veiculo v) {
        jdbc.update("UPDATE veiculo SET categoria_id = ?, marca = ?, modelo = ?, ano_fabricacao = ?, ano_modelo = ?, "
                + "quilometragem = ?, cor = ?, motor = ?, potencia_cv = ?, combustivel = ?, cambio = ?, "
                + "conservacao = ? WHERE id = ?",
                v.getCategoria().getId(), v.getMarca(), v.getModelo(), v.getAnoFabricacao(), v.getAnoModelo(),
                v.getQuilometragem(), v.getCor(), v.getMotor(), v.getPotenciaCv(), v.getCombustivel().name(),
                v.getCambio().name(), v.getConservacao().name(), v.getId());
    }
}
