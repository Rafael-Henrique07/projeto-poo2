package com.mycompany.projeto_poo2.config;

import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HttpSession sessao = request.getSession(false);
        Object usuario = sessao == null ? null : sessao.getAttribute(UsuarioSessao.CHAVE);
        if (usuario instanceof UsuarioSessao logado && logado.admin()) {
            return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}
