package com.mycompany.projeto_poo2.controller;

import com.mycompany.projeto_poo2.dto.AnuncioForm;
import com.mycompany.projeto_poo2.dto.FiltroAnuncio;
import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Anuncio;
import com.mycompany.projeto_poo2.model.EstadoConservacao;
import com.mycompany.projeto_poo2.model.StatusAnuncio;
import com.mycompany.projeto_poo2.model.TipoCambio;
import com.mycompany.projeto_poo2.model.TipoCombustivel;
import com.mycompany.projeto_poo2.selo.TipoSelo;
import com.mycompany.projeto_poo2.service.AnuncioServico;
import com.mycompany.projeto_poo2.service.CategoriaServico;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnuncioController {

    private final AnuncioServico anuncioServico;
    private final CategoriaServico categoriaServico;

    public AnuncioController(AnuncioServico anuncioServico, CategoriaServico categoriaServico) {
        this.anuncioServico = anuncioServico;
        this.categoriaServico = categoriaServico;
    }

    @GetMapping({"/", "/anuncios"})
    public String listar(@ModelAttribute("filtro") FiltroAnuncio filtro, Model model) {
        model.addAttribute("pagina", anuncioServico.pesquisar(filtro));
        model.addAttribute("categorias", categoriaServico.listar());
        model.addAttribute("selos", TipoSelo.values());
        model.addAttribute("estados", EstadoConservacao.values());
        return "anuncios/lista";
    }

    @GetMapping("/anuncios/{id}")
    public String detalhe(@PathVariable Integer id,
            @SessionAttribute(name = UsuarioSessao.CHAVE, required = false) UsuarioSessao usuario, Model model) {
        Anuncio anuncio = anuncioServico.buscarVisivel(id, usuario);
        model.addAttribute("anuncio", anuncio);
        model.addAttribute("ehDono", usuario != null && anuncio.pertenceA(usuario.id()));
        return "anuncios/detalhe";
    }

    @GetMapping("/anuncios/novo")
    public String novo(Model model) {
        model.addAttribute("form", new AnuncioForm());
        model.addAttribute("edicao", false);
        return prepararFormulario(model);
    }

    @PostMapping("/anuncios/novo")
    public String criar(@Valid @ModelAttribute("form") AnuncioForm form, BindingResult erros,
            @RequestParam(name = "fotos", required = false) List<MultipartFile> fotos,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model,
            RedirectAttributes atributos) {
        model.addAttribute("edicao", false);
        if (erros.hasErrors()) {
            return prepararFormulario(model);
        }
        try {
            Integer id = anuncioServico.criar(form, fotos, usuario.id());
            atributos.addFlashAttribute("sucesso", "Anúncio publicado.");
            return "redirect:/anuncios/" + id;
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return prepararFormulario(model);
        }
    }

    @GetMapping("/anuncios/{id}/editar")
    public String editar(@PathVariable Integer id, @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario,
            Model model) {
        model.addAttribute("form", AnuncioForm.de(anuncioServico.buscarDoAnunciante(id, usuario.id())));
        model.addAttribute("edicao", true);
        model.addAttribute("anuncioId", id);
        return prepararFormulario(model);
    }

    @PostMapping("/anuncios/{id}/editar")
    public String salvar(@PathVariable Integer id, @Valid @ModelAttribute("form") AnuncioForm form, BindingResult erros,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model,
            RedirectAttributes atributos) {
        model.addAttribute("edicao", true);
        model.addAttribute("anuncioId", id);
        if (erros.hasErrors()) {
            return prepararFormulario(model);
        }
        try {
            anuncioServico.atualizar(id, form, usuario.id());
            atributos.addFlashAttribute("sucesso", "Anúncio atualizado.");
            return "redirect:/anuncios/" + id;
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return prepararFormulario(model);
        }
    }

    @PostMapping("/anuncios/{id}/excluir")
    public String excluir(@PathVariable Integer id, @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario,
            RedirectAttributes atributos) {
        anuncioServico.excluir(id, usuario.id());
        atributos.addFlashAttribute("sucesso", "Anúncio excluído.");
        return "redirect:/meus-anuncios";
    }

    @PostMapping("/anuncios/{id}/status")
    public String alterarStatus(@PathVariable Integer id, @RequestParam StatusAnuncio status,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, RedirectAttributes atributos) {
        anuncioServico.alterarStatus(id, status, usuario.id());
        atributos.addFlashAttribute("sucesso", "Status do anúncio atualizado.");
        return "redirect:/meus-anuncios";
    }

    @GetMapping("/meus-anuncios")
    public String meusAnuncios(@SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model) {
        model.addAttribute("anuncios", anuncioServico.listarDoAnunciante(usuario.id()));
        return "anuncios/meus";
    }

    @GetMapping("/anuncios/{id}/fotos")
    public String fotos(@PathVariable Integer id, @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario,
            Model model) {
        model.addAttribute("anuncio", anuncioServico.buscarDoAnunciante(id, usuario.id()));
        return "anuncios/fotos";
    }

    @PostMapping("/anuncios/{id}/fotos")
    public String adicionarFotos(@PathVariable Integer id,
            @RequestParam(name = "fotos", required = false) List<MultipartFile> fotos,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, RedirectAttributes atributos) {
        anuncioServico.adicionarFotos(id, fotos, usuario.id());
        atributos.addFlashAttribute("sucesso", "Fotos adicionadas.");
        return redirecionarFotos(id);
    }

    @PostMapping("/anuncios/{id}/fotos/{fotoId}/principal")
    public String definirPrincipal(@PathVariable Integer id, @PathVariable Integer fotoId,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario) {
        anuncioServico.definirPrincipal(id, fotoId, usuario.id());
        return redirecionarFotos(id);
    }

    @PostMapping("/anuncios/{id}/fotos/{fotoId}/mover")
    public String moverFoto(@PathVariable Integer id, @PathVariable Integer fotoId, @RequestParam boolean paraCima,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario) {
        anuncioServico.moverFoto(id, fotoId, paraCima, usuario.id());
        return redirecionarFotos(id);
    }

    @PostMapping("/anuncios/{id}/fotos/{fotoId}/remover")
    public String removerFoto(@PathVariable Integer id, @PathVariable Integer fotoId,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario) {
        anuncioServico.removerFoto(id, fotoId, usuario.id());
        return redirecionarFotos(id);
    }

    private String redirecionarFotos(Integer id) {
        return "redirect:/anuncios/" + id + "/fotos";
    }

    private String prepararFormulario(Model model) {
        model.addAttribute("categorias", categoriaServico.listar());
        model.addAttribute("selos", TipoSelo.values());
        model.addAttribute("estados", EstadoConservacao.values());
        model.addAttribute("combustiveis", TipoCombustivel.values());
        model.addAttribute("cambios", TipoCambio.values());
        return "anuncios/form";
    }
}
