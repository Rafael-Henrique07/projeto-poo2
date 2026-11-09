package com.mycompany.projeto_poo2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Proposta {

    private final Integer id;
    private final BigDecimal valor;
    private final LocalDateTime dataProposta;
    private final StatusProposta status;

    public Proposta(Integer id, BigDecimal valor, LocalDateTime dataProposta, StatusProposta status) {
        this.id = id;
        this.valor = valor;
        this.dataProposta = dataProposta;
        this.status = status;
    }

    public boolean isPendente() {
        return status == StatusProposta.PENDENTE;
    }

    public boolean isAceita() {
        return status == StatusProposta.ACEITA;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataProposta() {
        return dataProposta;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
