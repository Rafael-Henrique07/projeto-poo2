package com.mycompany.projeto_poo2.model;

import java.time.LocalDateTime;
import java.util.List;

public class Negociacao {

    private final Integer id;
    private final Anuncio anuncio;
    private final Usuario comprador;
    private final LocalDateTime dataInicio;
    private final StatusNegociacao status;
    private List<Mensagem> mensagens = List.of();
    private List<Proposta> propostas = List.of();

    public Negociacao(Integer id, Anuncio anuncio, Usuario comprador, LocalDateTime dataInicio,
            StatusNegociacao status) {
        this.id = id;
        this.anuncio = anuncio;
        this.comprador = comprador;
        this.dataInicio = dataInicio;
        this.status = status;
    }

    public boolean isAberta() {
        return status == StatusNegociacao.ABERTA;
    }

    public boolean isComprador(Integer usuarioId) {
        return comprador.getId().equals(usuarioId);
    }

    public boolean isAnunciante(Integer usuarioId) {
        return anuncio.pertenceA(usuarioId);
    }

    public boolean isContatoLiberado() {
        return propostas.stream().anyMatch(Proposta::isAceita);
    }

    public boolean isPropostaPendente() {
        return propostas.stream().anyMatch(Proposta::isPendente);
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public Integer getId() {
        return id;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public StatusNegociacao getStatus() {
        return status;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }
}
