package com.mycompany.projeto_poo2.model;

public enum StatusNegociacao {
    ABERTA("Aberta"),
    ENCERRADA("Encerrada");

    private final String rotulo;

    StatusNegociacao(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
