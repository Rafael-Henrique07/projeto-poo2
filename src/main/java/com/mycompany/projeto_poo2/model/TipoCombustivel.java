package com.mycompany.projeto_poo2.model;

public enum TipoCombustivel {
    GASOLINA("Gasolina"),
    ETANOL("Etanol"),
    FLEX("Flex"),
    DIESEL("Diesel");

    private final String rotulo;

    TipoCombustivel(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
