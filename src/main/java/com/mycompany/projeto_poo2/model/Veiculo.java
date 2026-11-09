package com.mycompany.projeto_poo2.model;

public class Veiculo {

    private final Integer id;
    private final Categoria categoria;
    private final String marca;
    private final String modelo;
    private final int anoFabricacao;
    private final int anoModelo;
    private final int quilometragem;
    private final String cor;
    private final String motor;
    private final int potenciaCv;
    private final TipoCombustivel combustivel;
    private final TipoCambio cambio;
    private final EstadoConservacao conservacao;

    public Veiculo(Integer id, Categoria categoria, String marca, String modelo, int anoFabricacao, int anoModelo,
            int quilometragem, String cor, String motor, int potenciaCv, TipoCombustivel combustivel,
            TipoCambio cambio, EstadoConservacao conservacao) {
        this.id = id;
        this.categoria = categoria;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.quilometragem = quilometragem;
        this.cor = cor;
        this.motor = motor;
        this.potenciaCv = potenciaCv;
        this.combustivel = combustivel;
        this.cambio = cambio;
        this.conservacao = conservacao;
    }

    public Integer getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public String getCor() {
        return cor;
    }

    public String getMotor() {
        return motor;
    }

    public int getPotenciaCv() {
        return potenciaCv;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public TipoCambio getCambio() {
        return cambio;
    }

    public EstadoConservacao getConservacao() {
        return conservacao;
    }
}
