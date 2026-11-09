package com.mycompany.projeto_poo2.model;

public enum StatusAnuncio {
    ATIVO("Ativo"),
    PAUSADO("Pausado"),
    VENDIDO("Vendido"),
    REMOVIDO("Removido");

    private final String rotulo;

    StatusAnuncio(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
