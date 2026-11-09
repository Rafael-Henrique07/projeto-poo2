package com.mycompany.projeto_poo2.model;

public enum EstadoConservacao {
    ORIGINAL("Original"),
    RESTAURADO("Restaurado"),
    PREPARADO("Preparado"),
    PARA_RESTAURAR("Para restaurar");

    private final String rotulo;

    EstadoConservacao(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
