package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.UsuarioDAO;
import com.mycompany.projeto_poo2.dto.CadastroUsuarioForm;
import com.mycompany.projeto_poo2.model.Usuario;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioServico {

    private final ValidadorUsuario validador;
    private final UsuarioDAO usuarios;
    private final PasswordEncoder encoder;
    private final NotificadorUsuario notificador;

    public CadastroUsuarioServico(ValidadorUsuario validador, UsuarioDAO usuarios, PasswordEncoder encoder,
            NotificadorUsuario notificador) {
        this.validador = validador;
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.notificador = notificador;
    }

    @Transactional
    public void cadastrar(CadastroUsuarioForm form) {
        String email = form.getEmail().trim().toLowerCase(Locale.ROOT);
        validador.validarEmailDisponivel(email, null);
        validador.validarSenha(form.getSenha());

        Integer id = usuarios.inserir(Usuario.novo(form.getNome().trim(), email, encoder.encode(form.getSenha()),
                form.getTelefone().trim(), form.getCidade().trim(), form.getUf()));

        notificador.enviarBoasVindas(id);
    }
}
