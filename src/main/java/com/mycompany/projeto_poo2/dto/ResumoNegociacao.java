package com.mycompany.projeto_poo2.dto;

import com.mycompany.projeto_poo2.model.StatusNegociacao;
import java.time.LocalDateTime;

public class ResumoNegociacao {

    private final Integer id;
    private final String tituloAnuncio;
    private final String outraParte;
    private final LocalDateTime dataInicio;
    private final StatusNegociacao status;
    private final int naoLidas;

    public ResumoNegociacao(Integer id, String tituloAnuncio, String outraParte, LocalDateTime dataInicio,
            StatusNegociacao status, int naoLidas) {
        this.id = id;
        this.tituloAnuncio = tituloAnuncio;
        this.outraParte = outraParte;
        this.dataInicio = dataInicio;
        this.status = status;
        this.naoLidas = naoLidas;
    }

    public Integer getId() {
        return id;
    }

    public String getTituloAnuncio() {
        return tituloAnuncio;
    }

    public String getOutraParte() {
        return outraParte;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public StatusNegociacao getStatus() {
        return status;
    }

    public int getNaoLidas() {
        return naoLidas;
    }
}
