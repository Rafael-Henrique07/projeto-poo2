package com.mycompany.projeto_poo2.model;

public class Categoria {

    private final Integer id;
    private final String nome;
    private final String descricao;

    public Categoria(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
