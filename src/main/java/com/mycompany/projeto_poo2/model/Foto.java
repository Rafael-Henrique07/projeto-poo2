package com.mycompany.projeto_poo2.model;

public class Foto {

    private final Integer id;
    private final String caminhoArquivo;
    private final int ordem;
    private final boolean principal;

    public Foto(Integer id, String caminhoArquivo, int ordem, boolean principal) {
        this.id = id;
        this.caminhoArquivo = caminhoArquivo;
        this.ordem = ordem;
        this.principal = principal;
    }

    public Integer getId() {
        return id;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public int getOrdem() {
        return ordem;
    }

    public boolean isPrincipal() {
        return principal;
    }
}
