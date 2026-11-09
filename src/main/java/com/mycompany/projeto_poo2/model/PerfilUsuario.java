package com.mycompany.projeto_poo2.model;

public enum PerfilUsuario {
    COMUM("Comum"),
    ADMINISTRADOR("Administrador");

    private final String rotulo;

    PerfilUsuario(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
