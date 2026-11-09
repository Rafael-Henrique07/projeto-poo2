package com.mycompany.projeto_poo2.dto;

import java.util.List;

public class Pagina<T> {

    private final List<T> itens;
    private final int numero;
    private final int totalPaginas;
    private final int totalItens;

    public Pagina(List<T> itens, int numero, int totalPaginas, int totalItens) {
        this.itens = itens;
        this.numero = numero;
        this.totalPaginas = totalPaginas;
        this.totalItens = totalItens;
    }

    public boolean isTemAnterior() {
        return numero > 1;
    }

    public boolean isTemProxima() {
        return numero < totalPaginas;
    }

    public List<T> getItens() {
        return itens;
    }

    public int getNumero() {
        return numero;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getTotalItens() {
        return totalItens;
    }
}
