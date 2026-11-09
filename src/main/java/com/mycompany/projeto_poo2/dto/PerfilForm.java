package com.mycompany.projeto_poo2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PerfilForm {

    @NotBlank(message = "Informe o nome.")
    @Size(max = 100, message = "O nome pode ter até 100 caracteres.")
    private String nome;

    @NotBlank(message = "Informe o e-mail.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail pode ter até 150 caracteres.")
    private String email;

    @Pattern(regexp = "^[0-9()+\\- ]{10,20}$", message = "Telefone inválido. Informe DDD e número.")
    private String telefone = "";

    @NotBlank(message = "Informe a cidade.")
    @Size(max = 100, message = "A cidade pode ter até 100 caracteres.")
    private String cidade;

    @Pattern(regexp = "^(AC|AL|AM|AP|BA|CE|DF|ES|GO|MA|MG|MS|MT|PA|PB|PE|PI|PR|RJ|RN|RO|RR|RS|SC|SE|SP|TO)$",
            message = "Selecione a UF.")
    private String uf = "";

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
