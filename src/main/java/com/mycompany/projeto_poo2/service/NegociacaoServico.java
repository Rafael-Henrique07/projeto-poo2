package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.AnuncioDAO;
import com.mycompany.projeto_poo2.dao.MensagemDAO;
import com.mycompany.projeto_poo2.dao.NegociacaoDAO;
import com.mycompany.projeto_poo2.dao.PropostaDAO;
import com.mycompany.projeto_poo2.dao.UsuarioDAO;
import com.mycompany.projeto_poo2.dto.ResumoNegociacao;
import com.mycompany.projeto_poo2.exception.RecursoNaoEncontradoException;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Anuncio;
import com.mycompany.projeto_poo2.model.Negociacao;
import com.mycompany.projeto_poo2.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class NegociacaoServico {

    private static final int TAMANHO_MAXIMO = 1000;

    private final NegociacaoDAO negociacoes;
    private final MensagemDAO mensagens;
    private final PropostaDAO propostas;
    private final AnuncioDAO anuncios;
    private final UsuarioDAO usuarios;

    public NegociacaoServico(NegociacaoDAO negociacoes, MensagemDAO mensagens, PropostaDAO propostas,
            AnuncioDAO anuncios, UsuarioDAO usuarios) {
        this.negociacoes = negociacoes;
        this.mensagens = mensagens;
        this.propostas = propostas;
        this.anuncios = anuncios;
        this.usuarios = usuarios;
    }

    @Transactional
    public Integer iniciar(Integer anuncioId, Integer compradorId, String mensagem) {
        validarTexto(mensagem);
        Anuncio anuncio = buscarAnuncio(anuncioId);
        if (!anuncio.isAtivo()) {
            throw new RegraNegocioException("Este anúncio não está disponível para negociação.");
        }
        if (anuncio.pertenceA(compradorId)) {
            throw new RegraNegocioException("Você não pode negociar o próprio anúncio.");
        }
        Optional<NegociacaoDAO.Registro> existente = negociacoes.buscarPorAnuncioEComprador(anuncioId, compradorId);
        Integer negociacaoId;
        if (existente.isPresent()) {
            negociacaoId = existente.get().id();
            if (!existente.get().isAberta()) {
                negociacoes.reabrir(negociacaoId);
            }
        } else {
            negociacaoId = negociacoes.inserir(anuncioId, compradorId);
        }
        mensagens.inserir(negociacaoId, compradorId, mensagem.trim());
        return negociacaoId;
    }

    public Negociacao abrir(Integer id, Integer usuarioId) {
        Negociacao negociacao = carregar(id, usuarioId);
        negociacao.setMensagens(mensagens.listarPorNegociacao(id));
        negociacao.setPropostas(propostas.listarPorNegociacao(id));
        return negociacao;
    }

    @Transactional
    public void enviarMensagem(Integer id, Integer usuarioId, String texto) {
        validarTexto(texto);
        Negociacao negociacao = carregar(id, usuarioId);
        if (!negociacao.isAberta()) {
            throw new RegraNegocioException("Esta negociação está encerrada.");
        }
        mensagens.inserir(id, usuarioId, texto.trim());
    }

    public Negociacao carregar(Integer id, Integer usuarioId) {
        NegociacaoDAO.Registro registro = negociacoes.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Negociação não encontrada."));
        Anuncio anuncio = buscarAnuncio(registro.anuncioId());
        if (!registro.compradorId().equals(usuarioId) && !anuncio.pertenceA(usuarioId)) {
            throw new RecursoNaoEncontradoException("Negociação não encontrada.");
        }
        Usuario comprador = usuarios.buscarPorId(registro.compradorId()).orElseThrow();
        return new Negociacao(registro.id(), anuncio, comprador, registro.dataInicio(), registro.status());
    }

    public List<ResumoNegociacao> listarComoComprador(Integer usuarioId) {
        return negociacoes.listarComoComprador(usuarioId);
    }

    public List<ResumoNegociacao> listarComoAnunciante(Integer usuarioId) {
        return negociacoes.listarComoAnunciante(usuarioId);
    }

    private Anuncio buscarAnuncio(Integer anuncioId) {
        return anuncios.buscarPorId(anuncioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Anúncio não encontrado."));
    }

    private void validarTexto(String texto) {
        if (!StringUtils.hasText(texto) || texto.length() > TAMANHO_MAXIMO) {
            throw new RegraNegocioException("Escreva uma mensagem de até " + TAMANHO_MAXIMO + " caracteres.");
        }
    }
}
