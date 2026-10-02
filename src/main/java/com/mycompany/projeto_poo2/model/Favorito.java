package com.mycompany.projeto_poo2.model;

import java.time.LocalDateTime;

public class Favorito {
    private Integer id;
    private Usuario usuario;
    private Anuncio anuncio;
    private LocalDateTime dataFavoritado;

    public Favorito(Usuario usuario, Anuncio anuncio) {
        this.usuario = usuario;
        this.anuncio = anuncio;
        this.dataFavoritado = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public LocalDateTime getDataFavoritado() {
        return dataFavoritado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public void setDataFavoritado(LocalDateTime dataFavoritado) {
        this.dataFavoritado = dataFavoritado;
    }
}
