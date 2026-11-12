package com.mycompany.projeto_poo2.model;

import com.mycompany.projeto_poo2.selo.TipoSelo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Anuncio {

    private final Integer id;
    private final Usuario anunciante;
    private final Veiculo veiculo;
    private final String titulo;
    private final String descricao;
    private final BigDecimal preco;
    private final StatusAnuncio status;
    private final TipoSelo selo;
    private final LocalDateTime dataPublicacao;
    private final LocalDateTime dataAtualizacao;
    private List<Foto> fotos = List.of();

    public Anuncio(Integer id, Usuario anunciante, Veiculo veiculo, String titulo, String descricao, BigDecimal preco,
            StatusAnuncio status, TipoSelo selo, LocalDateTime dataPublicacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.anunciante = anunciante;
        this.veiculo = veiculo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.status = status;
        this.selo = selo;
        this.dataPublicacao = dataPublicacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public boolean pertenceA(Integer usuarioId) {
        return anunciante.getId().equals(usuarioId);
    }

    public boolean isAtivo() {
        return status == StatusAnuncio.ATIVO;
    }

    public boolean isRemovido() {
        return status == StatusAnuncio.REMOVIDO;
    }

    public String getCapa() {
        return fotos.isEmpty() ? null : fotos.get(0).getCaminhoArquivo();
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getAnunciante() {
        return anunciante;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public StatusAnuncio getStatus() {
        return status;
    }

    public TipoSelo getSelo() {
        return selo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}
