package com.mycompany.projeto_poo2.service;

import com.mycompany.projeto_poo2.exception.RegraNegocioException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArmazenamentoFotoServico {

    public static final int MAX_FOTOS = 15;

    private static final byte[] ASSINATURA_PNG = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    private final Path pasta;

    public ArmazenamentoFotoServico(@Value("${app.uploads.dir}") String pasta) {
        this.pasta = Path.of(pasta).toAbsolutePath().normalize();
    }

    public List<MultipartFile> validar(List<MultipartFile> arquivos, int jaExistentes) {
        List<MultipartFile> enviados = arquivos == null ? List.of()
                : arquivos.stream().filter(arquivo -> !arquivo.isEmpty()).toList();
        if (enviados.isEmpty() && jaExistentes == 0) {
            throw new RegraNegocioException("Envie pelo menos uma foto.");
        }
        if (enviados.size() + jaExistentes > MAX_FOTOS) {
            throw new RegraNegocioException("O anúncio pode ter no máximo " + MAX_FOTOS + " fotos.");
        }
        enviados.forEach(this::extensaoDe);
        return enviados;
    }

    public List<String> salvar(List<MultipartFile> arquivos) {
        List<String> nomes = new ArrayList<>();
        try {
            Files.createDirectories(pasta);
            for (MultipartFile arquivo : arquivos) {
                String nome = UUID.randomUUID() + "." + extensaoDe(arquivo);
                arquivo.transferTo(pasta.resolve(nome));
                nomes.add(nome);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível salvar a foto", e);
        }
        return nomes;
    }

    public void apagar(String nome) {
        try {
            Files.deleteIfExists(pasta.resolve(nome));
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível apagar a foto", e);
        }
    }

    private String extensaoDe(MultipartFile arquivo) {
        try (InputStream entrada = arquivo.getInputStream()) {
            byte[] inicio = entrada.readNBytes(8);
            if (inicio.length >= 3 && (inicio[0] & 0xFF) == 0xFF && (inicio[1] & 0xFF) == 0xD8
                    && (inicio[2] & 0xFF) == 0xFF) {
                return "jpg";
            }
            if (Arrays.equals(inicio, ASSINATURA_PNG)) {
                return "png";
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler a foto", e);
        }
        throw new RegraNegocioException("As fotos devem estar em JPG ou PNG.");
    }
}
