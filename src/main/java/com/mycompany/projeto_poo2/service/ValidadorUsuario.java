package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.UsuarioDAO;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuario {

    private final UsuarioDAO usuarios;

    public ValidadorUsuario(UsuarioDAO usuarios) {
        this.usuarios = usuarios;
    }

    public void validarEmailDisponivel(String email, Integer ignorarId) {
        if (usuarios.emailEmUso(email, ignorarId)) {
            throw new RegraNegocioException("Este e-mail já está cadastrado.");
        }
    }

    public void validarSenha(String senha) {
        if (senha == null || senha.length() < 8 || senha.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new RegraNegocioException("A senha deve ter de 8 a 72 caracteres.");
        }
    }
}
