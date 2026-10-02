package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.dao.UsuarioDAO;

public class CadastroUsuarioServico {
    
    private ValidadorUsuario validador = new ValidadorUsuario();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private NotificadorUsuario notificador = new NotificadorUsuario();
    
    public void cadastrar(Usuario usuario) {
        
        validador.validar(usuario);
        usuarioDAO.inserir(usuario);
        notificador.enviarBoasVindas(usuario);
        
    }
    
}