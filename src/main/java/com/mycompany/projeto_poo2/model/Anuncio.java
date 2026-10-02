package com.mycompany.projeto_poo2.model;

public class Anuncio {
    private Integer id;
    private Usuario anunciante;
    private String marca;
    private String modelo;
    private Integer ano;
    private String motorizacao;
    private Integer quilometragem;
    private String descricao;
    private String estadoConservacao;
    private Double valor;
    private String status;

    public Anuncio(Usuario anunciante, String marca, String modelo, Integer ano, String motorizacao, Integer quilometragem, String descricao, String estadoConservacao, Double valor, String status) {
        this.anunciante = anunciante;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.motorizacao = motorizacao;
        this.quilometragem = quilometragem;
        this.descricao = descricao;
        this.estadoConservacao = estadoConservacao;
        this.valor = valor;
        this.status = "disponível";
    }

    public Integer getId() {
        return id;
    }

    public Usuario getAnunciante() {
        return anunciante;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public String getMotorizacao() {
        return motorizacao;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public Double getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnunciante(Usuario anunciante) {
        this.anunciante = anunciante;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setMotorizacao(String motorizacao) {
        this.motorizacao = motorizacao;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
