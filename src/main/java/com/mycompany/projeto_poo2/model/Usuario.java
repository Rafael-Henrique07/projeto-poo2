package com.mycompany.projeto_poo2.model;

import java.time.LocalDateTime;

public class Usuario {

    private final Integer id;
    private final String nome;
    private final String email;
    private final String senhaHash;
    private final String telefone;
    private final String cidade;
    private final String uf;
    private final PerfilUsuario perfil;
    private final LocalDateTime dataCadastro;

    public Usuario(Integer id, String nome, String email, String senhaHash, String telefone, String cidade,
            String uf, PerfilUsuario perfil, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.cidade = cidade;
        this.uf = uf;
        this.perfil = perfil;
        this.dataCadastro = dataCadastro;
    }

    public static Usuario novo(String nome, String email, String senhaHash, String telefone, String cidade, String uf) {
        return new Usuario(null, nome, email, senhaHash, telefone, cidade, uf, PerfilUsuario.COMUM,
                LocalDateTime.now());
    }

    public static Usuario resumo(Integer id, String nome) {
        return new Usuario(id, nome, null, null, null, null, null, null, null);
    }

    public boolean isAdministrador() {
        return perfil == PerfilUsuario.ADMINISTRADOR;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
