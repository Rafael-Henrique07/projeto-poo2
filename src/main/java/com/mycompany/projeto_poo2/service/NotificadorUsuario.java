package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.model.Usuario;

public class NotificadorUsuario {
    
    public void enviarBoasVindas(Usuario usuario) {
        
        System.out.println("Enviando e-mail de boas vindas para " + usuario.getEmail());
        
    }
    
}