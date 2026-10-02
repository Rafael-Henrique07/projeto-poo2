package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.model.Usuario;

public class ValidadorUsuario {
    
    public void validar(Usuario usuario) {
        
        if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            
            throw new IllegalArgumentException("E-mail inválido.");
            
        }
        
    }
    
}