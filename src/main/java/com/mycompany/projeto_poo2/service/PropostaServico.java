package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.dao.MensagemDAO;
import com.mycompany.projeto_poo2.dao.PropostaDAO;
import com.mycompany.projeto_poo2.exception.RecursoNaoEncontradoException;
import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import com.mycompany.projeto_poo2.model.Negociacao;
import com.mycompany.projeto_poo2.model.Proposta;
import com.mycompany.projeto_poo2.model.StatusProposta;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropostaServico {

    private static final BigDecimal VALOR_MAXIMO = new BigDecimal("9999999999.99");

    private final PropostaDAO propostas;
    private final MensagemDAO mensagens;
    private final NegociacaoServico negociacoes;

    public PropostaServico(PropostaDAO propostas, MensagemDAO mensagens, NegociacaoServico negociacoes) {
        this.propostas = propostas;
        this.mensagens = mensagens;
        this.negociacoes = negociacoes;
    }

    @Transactional
    public void enviar(Integer negociacaoId, Integer compradorId, BigDecimal valor) {
        Negociacao negociacao = negociacoes.carregar(negociacaoId, compradorId);
        if (!negociacao.isComprador(compradorId)) {
            throw new RegraNegocioException("Só o comprador pode enviar propostas.");
        }
        exigirDisponivel(negociacao);
        if (valor == null || valor.signum() <= 0 || valor.scale() > 2 || valor.compareTo(VALOR_MAXIMO) > 0) {
            throw new RegraNegocioException("Informe um valor maior que zero, com até duas casas decimais.");
        }
        propostas.cancelarPendentes(negociacaoId);
        propostas.inserir(negociacaoId, valor);
        mensagens.inserir(negociacaoId, compradorId, "Proposta enviada: " + formatar(valor));
    }

    @Transactional
    public void responder(Integer negociacaoId, Integer propostaId, Integer anuncianteId, boolean aceitar) {
        Negociacao negociacao = negociacoes.carregar(negociacaoId, anuncianteId);
        if (!negociacao.isAnunciante(anuncianteId)) {
            throw new RegraNegocioException("Só o anunciante pode responder propostas.");
        }
        exigirDisponivel(negociacao);
        Proposta proposta = propostas.buscar(propostaId, negociacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Proposta não encontrada."));
        if (!propostas.responder(propostaId, aceitar ? StatusProposta.ACEITA : StatusProposta.RECUSADA)) {
            throw new RegraNegocioException("Esta proposta já foi respondida.");
        }
        String resposta = aceitar ? "Proposta aceita: " : "Proposta recusada: ";
        mensagens.inserir(negociacaoId, anuncianteId, resposta + formatar(proposta.getValor()));
    }

    private void exigirDisponivel(Negociacao negociacao) {
        if (!negociacao.isAberta() || !negociacao.getAnuncio().isAtivo()) {
            throw new RegraNegocioException("Esta negociação não aceita propostas no momento.");
        }
    }

    private String formatar(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(valor);
    }
}
