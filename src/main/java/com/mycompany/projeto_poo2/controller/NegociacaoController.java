package com.mycompany.projeto_poo2.controller;

import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import com.mycompany.projeto_poo2.model.Negociacao;
import com.mycompany.projeto_poo2.service.NegociacaoServico;
import com.mycompany.projeto_poo2.service.PropostaServico;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NegociacaoController {

    private final NegociacaoServico negociacaoServico;
    private final PropostaServico propostaServico;

    public NegociacaoController(NegociacaoServico negociacaoServico, PropostaServico propostaServico) {
        this.negociacaoServico = negociacaoServico;
        this.propostaServico = propostaServico;
    }

    @PostMapping("/anuncios/{id}/negociacoes")
    public String iniciar(@PathVariable Integer id, @RequestParam String mensagem,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario) {
        return "redirect:/negociacoes/" + negociacaoServico.iniciar(id, usuario.id(), mensagem);
    }

    @GetMapping("/negociacoes")
    public String listar(@SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model) {
        model.addAttribute("comoComprador", negociacaoServico.listarComoComprador(usuario.id()));
        model.addAttribute("comoAnunciante", negociacaoServico.listarComoAnunciante(usuario.id()));
        return "negociacoes/lista";
    }

    @GetMapping("/negociacoes/{id}")
    public String detalhe(@PathVariable Integer id, @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario,
            Model model) {
        Negociacao negociacao = negociacaoServico.abrir(id, usuario.id());
        model.addAttribute("negociacao", negociacao);
        model.addAttribute("ehComprador", negociacao.isComprador(usuario.id()));
        model.addAttribute("ehAnunciante", negociacao.isAnunciante(usuario.id()));
        return "negociacoes/detalhe";
    }

    @PostMapping("/negociacoes/{id}/mensagens")
    public String enviarMensagem(@PathVariable Integer id, @RequestParam String conteudo,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario) {
        negociacaoServico.enviarMensagem(id, usuario.id(), conteudo);
        return "redirect:/negociacoes/" + id;
    }

    @PostMapping("/negociacoes/{id}/propostas")
    public String enviarProposta(@PathVariable Integer id, @RequestParam(required = false) BigDecimal valor,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, RedirectAttributes atributos) {
        propostaServico.enviar(id, usuario.id(), valor);
        atributos.addFlashAttribute("sucesso", "Proposta enviada ao anunciante.");
        return "redirect:/negociacoes/" + id;
    }

    @PostMapping("/negociacoes/{id}/propostas/{propostaId}/resposta")
    public String responderProposta(@PathVariable Integer id, @PathVariable Integer propostaId,
            @RequestParam boolean aceitar, @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario,
            RedirectAttributes atributos) {
        propostaServico.responder(id, propostaId, usuario.id(), aceitar);
        atributos.addFlashAttribute("sucesso", aceitar ? "Proposta aceita." : "Proposta recusada.");
        return "redirect:/negociacoes/" + id;
    }
}
