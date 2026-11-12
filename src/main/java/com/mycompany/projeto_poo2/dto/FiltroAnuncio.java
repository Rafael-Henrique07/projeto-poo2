package com.mycompany.projeto_poo2.dto;

import com.mycompany.projeto_poo2.model.EstadoConservacao;
import com.mycompany.projeto_poo2.selo.TipoSelo;
import java.math.BigDecimal;

public class FiltroAnuncio {

    private String busca;
    private Integer categoriaId;
    private String marca;
    private Integer anoMin;
    private Integer anoMax;
    private BigDecimal precoMin;
    private BigDecimal precoMax;
    private EstadoConservacao conservacao;
    private String cidade;
    private String uf;
    private TipoSelo selo;

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAnoMin() {
        return anoMin;
    }

    public void setAnoMin(Integer anoMin) {
        this.anoMin = anoMin;
    }

    public Integer getAnoMax() {
        return anoMax;
    }

    public void setAnoMax(Integer anoMax) {
        this.anoMax = anoMax;
    }

    public BigDecimal getPrecoMin() {
        return precoMin;
    }

    public void setPrecoMin(BigDecimal precoMin) {
        this.precoMin = precoMin;
    }

    public BigDecimal getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(BigDecimal precoMax) {
        this.precoMax = precoMax;
    }

    public EstadoConservacao getConservacao() {
        return conservacao;
    }

    public void setConservacao(EstadoConservacao conservacao) {
        this.conservacao = conservacao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public TipoSelo getSelo() {
        return selo;
    }

    public void setSelo(TipoSelo selo) {
        this.selo = selo;
    }
}
