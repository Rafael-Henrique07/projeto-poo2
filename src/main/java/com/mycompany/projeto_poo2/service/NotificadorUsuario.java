package com.mycompany.projeto_poo2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorUsuario {

    private static final Logger log = LoggerFactory.getLogger(NotificadorUsuario.class);

    public void enviarBoasVindas(Integer usuarioId) {
        log.info("[simulacao] E-mail de boas-vindas para o usuario id={}", usuarioId);
    }
}
