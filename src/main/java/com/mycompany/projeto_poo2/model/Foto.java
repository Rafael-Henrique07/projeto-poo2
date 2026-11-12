package com.mycompany.projeto_poo2.model;

public class Foto {

    private final Integer id;
    private final String caminhoArquivo;

    public Foto(Integer id, String caminhoArquivo) {
        this.id = id;
        this.caminhoArquivo = caminhoArquivo;
    }

    public Integer getId() {
        return id;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }
}
