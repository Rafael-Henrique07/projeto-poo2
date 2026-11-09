package com.mycompany.projeto_poo2.selo;

public enum TipoSelo {
    ANTIGO(new SeloAntigo()),
    CLASSICO(new SeloClassico()),
    PREPARADO(new SeloPreparado());

    private final Selo selo;

    TipoSelo(Selo selo) {
        this.selo = selo;
    }

    public String getRotulo() {
        return selo.gerar();
    }
}
