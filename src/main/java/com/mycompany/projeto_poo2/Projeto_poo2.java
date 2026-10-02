package com.mycompany.projeto_poo2;

import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.service.CadastroUsuarioServico;

public class Projeto_poo2 {
    
    public static void main(String[] args) {
        
        Usuario usuario = new Usuario(null, "Ludmila", "ludmila@email.com", "123456", "43999999999", true, false);
        
        CadastroUsuarioServico servico = new CadastroUsuarioServico();
        servico.cadastrar(usuario);
    }
    
}