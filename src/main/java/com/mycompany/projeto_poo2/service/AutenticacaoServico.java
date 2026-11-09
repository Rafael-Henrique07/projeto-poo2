package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.UsuarioDAO;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Usuario;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServico {

    private final UsuarioDAO usuarios;
    private final PasswordEncoder encoder;

    public AutenticacaoServico(UsuarioDAO usuarios, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.encoder = encoder;
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarios.buscarPorEmail(email.trim().toLowerCase(Locale.ROOT))
                .filter(u -> encoder.matches(senha, u.getSenhaHash()))
                .orElseThrow(() -> new RegraNegocioException("E-mail ou senha inválidos."));
        if (!usuario.isAtivo()) {
            throw new RegraNegocioException("Esta conta foi desativada.");
        }
        return usuario;
    }
}
