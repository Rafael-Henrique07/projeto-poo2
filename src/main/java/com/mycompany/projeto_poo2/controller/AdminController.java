package com.mycompany.projeto_poo2.controller;

import com.mycompany.projeto_poo2.dto.CategoriaForm;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Categoria;
import com.mycompany.projeto_poo2.service.CategoriaServico;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoriaServico categoriaServico;

    public AdminController(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute("categorias", categoriaServico.listar());
        return "admin/categorias";
    }

    @GetMapping("/categorias/nova")
    public String novaCategoria(Model model) {
        model.addAttribute("form", new CategoriaForm());
        return "admin/categoria-form";
    }

    @PostMapping("/categorias/nova")
    public String criarCategoria(@Valid @ModelAttribute("form") CategoriaForm form, BindingResult erros, Model model,
            RedirectAttributes atributos) {
        if (erros.hasErrors()) {
            return "admin/categoria-form";
        }
        try {
            categoriaServico.criar(form);
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "admin/categoria-form";
        }
        atributos.addFlashAttribute("sucesso", "Categoria cadastrada.");
        return "redirect:/admin/categorias";
    }

    @GetMapping("/categorias/{id}/editar")
    public String editarCategoria(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaServico.buscar(id);
        CategoriaForm form = new CategoriaForm();
        form.setNome(categoria.getNome());
        form.setDescricao(categoria.getDescricao());
        model.addAttribute("form", form);
        model.addAttribute("categoriaId", id);
        return "admin/categoria-form";
    }

    @PostMapping("/categorias/{id}/editar")
    public String salvarCategoria(@PathVariable Integer id, @Valid @ModelAttribute("form") CategoriaForm form,
            BindingResult erros, Model model, RedirectAttributes atributos) {
        model.addAttribute("categoriaId", id);
        if (erros.hasErrors()) {
            return "admin/categoria-form";
        }
        try {
            categoriaServico.atualizar(id, form);
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "admin/categoria-form";
        }
        atributos.addFlashAttribute("sucesso", "Categoria atualizada.");
        return "redirect:/admin/categorias";
    }

    @PostMapping("/categorias/{id}/excluir")
    public String excluirCategoria(@PathVariable Integer id, RedirectAttributes atributos) {
        categoriaServico.excluir(id);
        atributos.addFlashAttribute("sucesso", "Categoria excluída.");
        return "redirect:/admin/categorias";
    }
}
