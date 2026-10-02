package com.mycompany.projeto_poo2.model;

public class Foto {
    private Integer id;
    private Anuncio anuncio;
    private String caminhoArquivo;

    public Foto(Anuncio anuncio, String caminhoArquivo) {
        this.anuncio = anuncio;
        this.caminhoArquivo = caminhoArquivo;
    }

    public Integer getId() {
        return id;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}
