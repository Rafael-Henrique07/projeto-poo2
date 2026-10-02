package com.mycompany.projeto_poo2.model;

import java.time.LocalDateTime;

public class Contato {
    private Integer id;
    private Usuario comprador;
    private Usuario anunciante;
    private Anuncio anuncio;
    private String mensagem;
    private LocalDateTime data;

    public Contato(Usuario comprador, Usuario anunciante, Anuncio anuncio, String mensagem) {
        this.comprador = comprador;
        this.anunciante = anunciante;
        this.anuncio = anuncio;
        this.mensagem = mensagem;
        this.data = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Usuario getAnunciante() {
        return anunciante;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public void setAnunciante(Usuario anunciante) {
        this.anunciante = anunciante;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
