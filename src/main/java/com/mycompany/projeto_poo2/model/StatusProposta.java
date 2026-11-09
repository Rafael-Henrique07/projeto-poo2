package com.mycompany.projeto_poo2.model;

public enum StatusProposta {
    PENDENTE("Pendente"),
    ACEITA("Aceita"),
    RECUSADA("Recusada"),
    CANCELADA("Cancelada");

    private final String rotulo;

    StatusProposta(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
