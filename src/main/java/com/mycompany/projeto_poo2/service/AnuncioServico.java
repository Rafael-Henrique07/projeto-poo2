package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.AnuncioDAO;
import com.mycompany.projeto_poo2.dao.FotoDAO;
import com.mycompany.projeto_poo2.dao.NegociacaoDAO;
import com.mycompany.projeto_poo2.dao.VeiculoDAO;
import com.mycompany.projeto_poo2.dto.AnuncioForm;
import com.mycompany.projeto_poo2.dto.FiltroAnuncio;
import com.mycompany.projeto_poo2.dto.UsuarioSessao;
import com.mycompany.projeto_poo2.exception.RecursoNaoEncontradoException;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Anuncio;
import com.mycompany.projeto_poo2.model.Categoria;
import com.mycompany.projeto_poo2.model.Foto;
import com.mycompany.projeto_poo2.model.StatusAnuncio;
import com.mycompany.projeto_poo2.model.Veiculo;
import java.time.Year;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnuncioServico {

    private final AnuncioDAO anuncios;
    private final VeiculoDAO veiculos;
    private final FotoDAO fotos;
    private final NegociacaoDAO negociacoes;
    private final CategoriaServico categorias;
    private final ArmazenamentoFotoServico armazenamento;

    public AnuncioServico(AnuncioDAO anuncios, VeiculoDAO veiculos, FotoDAO fotos, NegociacaoDAO negociacoes,
            CategoriaServico categorias, ArmazenamentoFotoServico armazenamento) {
        this.anuncios = anuncios;
        this.veiculos = veiculos;
        this.fotos = fotos;
        this.negociacoes = negociacoes;
        this.categorias = categorias;
        this.armazenamento = armazenamento;
    }

    public List<Anuncio> pesquisar(FiltroAnuncio filtro) {
        return anuncios.pesquisar(filtro);
    }

    public Anuncio buscar(Integer id) {
        Anuncio anuncio = anuncios.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Anúncio não encontrado."));
        anuncio.setFotos(fotos.listarPorAnuncio(id));
        return anuncio;
    }

    public Anuncio buscarVisivel(Integer id, UsuarioSessao usuario) {
        Anuncio anuncio = buscar(id);
        boolean dono = usuario != null && anuncio.pertenceA(usuario.id());
        boolean oculto = anuncio.getStatus() == StatusAnuncio.PAUSADO && !dono;
        if (anuncio.isRemovido() || oculto) {
            throw new RecursoNaoEncontradoException("Anúncio não encontrado.");
        }
        return anuncio;
    }

    public Anuncio buscarDoAnunciante(Integer id, Integer usuarioId) {
        Anuncio anuncio = buscar(id);
        if (!anuncio.pertenceA(usuarioId)) {
            throw new RegraNegocioException("Só o anunciante pode alterar este anúncio.");
        }
        if (anuncio.isRemovido()) {
            throw new RecursoNaoEncontradoException("Anúncio não encontrado.");
        }
        return anuncio;
    }

    public List<Anuncio> listarDoAnunciante(Integer usuarioId) {
        return anuncios.listarPorAnunciante(usuarioId);
    }

    @Transactional
    public Integer criar(AnuncioForm form, List<MultipartFile> arquivos, Integer usuarioId) {
        validarAnos(form);
        List<MultipartFile> enviados = armazenamento.validar(arquivos, 0);
        Integer veiculoId = veiculos.inserir(montarVeiculo(null, form));
        Integer anuncioId = anuncios.inserir(usuarioId, veiculoId, form.getTitulo().trim(), form.getDescricao().trim(),
                form.getPreco(), form.getSelo());
        gravarFotos(anuncioId, enviados);
        return anuncioId;
    }

    @Transactional
    public void atualizar(Integer id, AnuncioForm form, Integer usuarioId) {
        Anuncio anuncio = buscarDoAnunciante(id, usuarioId);
        validarAnos(form);
        veiculos.atualizar(montarVeiculo(anuncio.getVeiculo().getId(), form));
        anuncios.atualizar(id, form.getTitulo().trim(), form.getDescricao().trim(), form.getPreco(), form.getSelo());
    }

    @Transactional
    public void excluir(Integer id, Integer usuarioId) {
        buscarDoAnunciante(id, usuarioId);
        anuncios.atualizarStatus(id, StatusAnuncio.REMOVIDO);
        negociacoes.encerrarPorAnuncio(id);
    }

    @Transactional
    public void alterarStatus(Integer id, StatusAnuncio status, Integer usuarioId) {
        buscarDoAnunciante(id, usuarioId);
        if (status == StatusAnuncio.REMOVIDO) {
            throw new RegraNegocioException("Status inválido.");
        }
        anuncios.atualizarStatus(id, status);
        if (status == StatusAnuncio.VENDIDO) {
            negociacoes.encerrarPorAnuncio(id);
        }
    }

    @Transactional
    public void adicionarFotos(Integer id, List<MultipartFile> arquivos, Integer usuarioId) {
        Anuncio anuncio = buscarDoAnunciante(id, usuarioId);
        int existentes = anuncio.getFotos().size();
        List<MultipartFile> enviados = armazenamento.validar(arquivos, existentes);
        if (enviados.isEmpty()) {
            throw new RegraNegocioException("Selecione pelo menos uma foto.");
        }
        gravarFotos(id, enviados);
    }

    @Transactional
    public void removerFoto(Integer id, Integer fotoId, Integer usuarioId) {
        Anuncio anuncio = buscarDoAnunciante(id, usuarioId);
        Foto foto = localizar(anuncio, fotoId);
        if (anuncio.getFotos().size() == 1) {
            throw new RegraNegocioException("O anúncio precisa ter pelo menos uma foto.");
        }
        fotos.remover(fotoId);
        armazenamento.apagar(foto.getCaminhoArquivo());
    }

    private void gravarFotos(Integer anuncioId, List<MultipartFile> enviados) {
        for (String nome : armazenamento.salvar(enviados)) {
            fotos.inserir(anuncioId, nome);
        }
    }

    private Foto localizar(Anuncio anuncio, Integer fotoId) {
        return anuncio.getFotos().stream()
                .filter(foto -> foto.getId().equals(fotoId))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Foto não encontrada."));
    }

    private void validarAnos(AnuncioForm form) {
        int limite = Year.now().getValue() + 1;
        if (form.getAnoFabricacao() > limite || form.getAnoModelo() > limite) {
            throw new RegraNegocioException("O ano informado ainda não chegou.");
        }
        if (form.getAnoModelo() < form.getAnoFabricacao() || form.getAnoModelo() > form.getAnoFabricacao() + 1) {
            throw new RegraNegocioException("O ano do modelo deve ser igual ao de fabricação ou o ano seguinte.");
        }
    }

    private Veiculo montarVeiculo(Integer id, AnuncioForm form) {
        Categoria categoria = categoriaDe(form);
        return new Veiculo(id, categoria, form.getMarca().trim(), form.getModelo().trim(), form.getAnoFabricacao(),
                form.getAnoModelo(), form.getQuilometragem(), form.getCor().trim(), form.getMotor().trim(),
                form.getPotenciaCv(), form.getCombustivel(), form.getCambio(), form.getConservacao());
    }

    private Categoria categoriaDe(AnuncioForm form) {
        try {
            return categorias.buscar(form.getCategoriaId());
        } catch (RecursoNaoEncontradoException e) {
            throw new RegraNegocioException("Categoria inválida.");
        }
    }
}
