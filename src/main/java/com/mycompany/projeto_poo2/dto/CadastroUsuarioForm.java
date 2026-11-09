package com.mycompany.projeto_poo2.dto;

import jakarta.validation.constraints.Size;

public class CadastroUsuarioForm extends PerfilForm {

    @Size(min = 8, max = 72, message = "A senha deve ter de 8 a 72 caracteres.")
    private String senha = "";

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
