package com.mycompany.projeto_poo2.controller;

import com.mycompany.projeto_poo2.dto.CadastroUsuarioForm;
import com.mycompany.projeto_poo2.dto.PerfilForm;
import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Usuario;
import com.mycompany.projeto_poo2.service.AutenticacaoServico;
import com.mycompany.projeto_poo2.service.CadastroUsuarioServico;
import com.mycompany.projeto_poo2.service.PerfilServico;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final CadastroUsuarioServico cadastroServico;
    private final AutenticacaoServico autenticacaoServico;
    private final PerfilServico perfilServico;

    public UsuarioController(CadastroUsuarioServico cadastroServico, AutenticacaoServico autenticacaoServico,
            PerfilServico perfilServico) {
        this.cadastroServico = cadastroServico;
        this.autenticacaoServico = autenticacaoServico;
        this.perfilServico = perfilServico;
    }

    @GetMapping("/cadastro")
    public String formularioCadastro(Model model) {
        model.addAttribute("form", new CadastroUsuarioForm());
        return "usuarios/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("form") CadastroUsuarioForm form, BindingResult erros, Model model,
            RedirectAttributes atributos) {
        if (erros.hasErrors()) {
            return "usuarios/cadastro";
        }
        try {
            cadastroServico.cadastrar(form);
            atributos.addFlashAttribute("sucesso", "Cadastro realizado. Entre com seu e-mail e senha.");
            return "redirect:/login";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "usuarios/cadastro";
        }
    }

    @GetMapping("/login")
    public String formularioLogin() {
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String entrar(@RequestParam String email, @RequestParam String senha, Model model,
            HttpServletRequest request) {
        try {
            iniciarSessao(request, autenticacaoServico.autenticar(email, senha));
            return "redirect:/";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("email", email);
            return "usuarios/login";
        }
    }

    @PostMapping("/logout")
    public String sair(HttpSession sessao) {
        sessao.invalidate();
        return "redirect:/";
    }

    @GetMapping("/perfil")
    public String perfil(@SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model) {
        Usuario atual = perfilServico.buscar(usuario.id());
        PerfilForm form = new PerfilForm();
        form.setNome(atual.getNome());
        form.setEmail(atual.getEmail());
        form.setTelefone(atual.getTelefone());
        form.setCidade(atual.getCidade());
        form.setUf(atual.getUf());
        model.addAttribute("form", form);
        return "usuarios/perfil";
    }

    @PostMapping("/perfil")
    public String salvarPerfil(@Valid @ModelAttribute("form") PerfilForm form, BindingResult erros,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, Model model, HttpSession sessao,
            RedirectAttributes atributos) {
        if (erros.hasErrors()) {
            return "usuarios/perfil";
        }
        try {
            perfilServico.atualizar(usuario.id(), form);
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "usuarios/perfil";
        }
        sessao.setAttribute(UsuarioSessao.CHAVE,
                new UsuarioSessao(usuario.id(), form.getNome().trim(), usuario.admin()));
        atributos.addFlashAttribute("sucesso", "Dados atualizados.");
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/senha")
    public String alterarSenha(@RequestParam String senhaAtual, @RequestParam String novaSenha,
            @SessionAttribute(UsuarioSessao.CHAVE) UsuarioSessao usuario, RedirectAttributes atributos) {
        perfilServico.alterarSenha(usuario.id(), senhaAtual, novaSenha);
        atributos.addFlashAttribute("sucesso", "Senha alterada.");
        return "redirect:/perfil";
    }

    private void iniciarSessao(HttpServletRequest request, Usuario usuario) {
        request.getSession(true);
        request.changeSessionId();
        request.getSession().setAttribute(UsuarioSessao.CHAVE,
                new UsuarioSessao(usuario.getId(), usuario.getNome(), usuario.isAdministrador()));
    }
}
