package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.UsuarioDAO;
import com.mycompany.projeto_poo2.dto.PerfilForm;
import com.mycompany.projeto_poo2.exception.RecursoNaoEncontradoException;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Usuario;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilServico {

    private final UsuarioDAO usuarios;
    private final ValidadorUsuario validador;
    private final PasswordEncoder encoder;

    public PerfilServico(UsuarioDAO usuarios, ValidadorUsuario validador, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.validador = validador;
        this.encoder = encoder;
    }

    public Usuario buscar(Integer id) {
        return usuarios.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
    }

    @Transactional
    public void atualizar(Integer id, PerfilForm form) {
        String email = form.getEmail().trim().toLowerCase(Locale.ROOT);
        validador.validarEmailDisponivel(email, id);
        usuarios.atualizarDados(id, form.getNome().trim(), email, form.getTelefone().trim(), form.getCidade().trim(),
                form.getUf());
    }

    @Transactional
    public void alterarSenha(Integer id, String senhaAtual, String novaSenha) {
        if (!encoder.matches(senhaAtual, buscar(id).getSenhaHash())) {
            throw new RegraNegocioException("A senha atual está incorreta.");
        }
        validador.validarSenha(novaSenha);
        usuarios.atualizarSenha(id, encoder.encode(novaSenha));
    }
}
