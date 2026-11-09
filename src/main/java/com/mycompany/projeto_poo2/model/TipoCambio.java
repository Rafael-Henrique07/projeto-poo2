package com.mycompany.projeto_poo2.model;

public enum TipoCambio {
    MANUAL("Manual"),
    AUTOMATICO("Automático");

    private final String rotulo;

    TipoCambio(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
