package com.mycompany.projeto_poo2;

import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.service.CadastroUsuarioServico;
import java.sql.SQLException;

public class Projeto_poo2 {

    public static void main(String[] args) {
        Usuario usuario = new Usuario("Maria", "maria@email.com", "123456", "43999999999");

        try {
            CadastroUsuarioServico servico = new CadastroUsuarioServico();
            servico.cadastrar(usuario);
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
}