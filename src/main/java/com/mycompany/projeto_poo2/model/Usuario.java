package com.mycompany.projeto_poo2.model;

public class Usuario {
    
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private boolean comprador;
    private boolean anunciante;
    
    // Construtor para cadastrar um usuário novo (rega de negócio automática)
    public Usuario(String nome, String email, String senha, String telefone) {
        
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.comprador = true;
        this.anunciante = false;
        
    }

    // Construtor para reconstituir um usuário que já existe no banco
    public Usuario(Integer id, String nome, String email, String senha, String telefone, boolean comprador, boolean anunciante) {
        
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.comprador = comprador;
        this.anunciante = anunciante;
        
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

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isComprador() {
        return comprador;
    }

    public boolean isAnunciante() {
        return anunciante;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setComprador(boolean comprador) {
        this.comprador = comprador;
    }

    public void setAnunciante(boolean anunciante) {
        this.anunciante = anunciante;
    }
    
    public void tornarAnunciante() {
        
        this.anunciante = true;
        
    }
}
