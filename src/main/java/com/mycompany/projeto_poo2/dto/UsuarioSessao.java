package com.mycompany.projeto_poo2.dto;

import java.io.Serializable;

public record UsuarioSessao(Integer id, String nome, boolean admin) implements Serializable {

    public static final String CHAVE = "usuarioLogado";
}
