package com.mycompany.projeto_poo2.model;

import java.time.LocalDateTime;

public class Mensagem {

    private final Integer id;
    private final Usuario remetente;
    private final String conteudo;
    private final LocalDateTime dataEnvio;
    private final boolean lida;

    public Mensagem(Integer id, Usuario remetente, String conteudo, LocalDateTime dataEnvio, boolean lida) {
        this.id = id;
        this.remetente = remetente;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.lida = lida;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public boolean isLida() {
        return lida;
    }
}
