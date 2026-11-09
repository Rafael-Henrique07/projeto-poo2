package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.CategoriaDAO;
import com.mycompany.projeto_poo2.dto.CategoriaForm;
import com.mycompany.projeto_poo2.exception.RecursoNaoEncontradoException;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Categoria;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CategoriaServico {

    private final CategoriaDAO categorias;

    public CategoriaServico(CategoriaDAO categorias) {
        this.categorias = categorias;
    }

    public List<Categoria> listar() {
        return categorias.listar();
    }

    public Categoria buscar(Integer id) {
        return categorias.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));
    }

    @Transactional
    public void criar(CategoriaForm form) {
        validarNome(form.getNome().trim(), null);
        categorias.inserir(form.getNome().trim(), descricaoDe(form));
    }

    @Transactional
    public void atualizar(Integer id, CategoriaForm form) {
        buscar(id);
        validarNome(form.getNome().trim(), id);
        categorias.atualizar(id, form.getNome().trim(), descricaoDe(form));
    }

    @Transactional
    public void excluir(Integer id) {
        buscar(id);
        if (categorias.possuiVeiculos(id)) {
            throw new RegraNegocioException("Esta categoria possui veículos vinculados e não pode ser excluída.");
        }
        categorias.excluir(id);
    }

    private void validarNome(String nome, Integer ignorarId) {
        if (categorias.nomeEmUso(nome, ignorarId)) {
            throw new RegraNegocioException("Já existe uma categoria com este nome.");
        }
    }

    private String descricaoDe(CategoriaForm form) {
        return StringUtils.hasText(form.getDescricao()) ? form.getDescricao().trim() : null;
    }
}
