package com.mycompany.projeto_poo2.controller;

import com.mycompany.projeto_poo2.dto.Ufs;
import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("usuarioLogado")
    public UsuarioSessao usuarioLogado(
            @SessionAttribute(name = UsuarioSessao.CHAVE, required = false) UsuarioSessao usuario) {
        return usuario;
    }

    @ModelAttribute("ufs")
    public List<String> ufs() {
        return Ufs.TODAS;
    }

    @ExceptionHandler(RegraNegocioException.class)
    public String regraNegocio(RegraNegocioException e, HttpServletRequest request, RedirectAttributes atributos) {
        atributos.addFlashAttribute("erro", e.getMessage());
        return "redirect:" + paginaAnterior(request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String arquivoGrandeDemais(HttpServletRequest request, RedirectAttributes atributos) {
        atributos.addFlashAttribute("erro", "Cada foto pode ter no máximo 5 MB.");
        return "redirect:" + paginaAnterior(request);
    }

    private String paginaAnterior(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null) {
            return "/";
        }
        try {
            URI uri = URI.create(referer);
            boolean mesmoServidor = request.getServerName().equalsIgnoreCase(uri.getHost());
            String caminho = uri.getRawPath();
            if (mesmoServidor && caminho != null && caminho.startsWith("/") && !caminho.startsWith("//")) {
                return caminho;
            }
        } catch (IllegalArgumentException e) {
            return "/";
        }
        return "/";
    }
}
