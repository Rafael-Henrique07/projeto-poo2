package com.mycompany.projeto_poo2.dto;

import com.mycompany.projeto_poo2.model.Anuncio;
import com.mycompany.projeto_poo2.model.EstadoConservacao;
import com.mycompany.projeto_poo2.model.TipoCambio;
import com.mycompany.projeto_poo2.model.TipoCombustivel;
import com.mycompany.projeto_poo2.model.Veiculo;
import com.mycompany.projeto_poo2.selo.TipoSelo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class AnuncioForm {

    @NotBlank(message = "Informe o título.")
    @Size(max = 120, message = "O título pode ter até 120 caracteres.")
    private String titulo;

    @NotBlank(message = "Escreva uma descrição.")
    @Size(max = 2000, message = "A descrição pode ter até 2000 caracteres.")
    private String descricao;

    @NotNull(message = "Informe o preço.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Digits(integer = 10, fraction = 2, message = "Preço inválido.")
    private BigDecimal preco;

    @NotNull(message = "Escolha a categoria.")
    private Integer categoriaId;

    @NotBlank(message = "Informe a marca.")
    @Size(max = 60, message = "A marca pode ter até 60 caracteres.")
    private String marca;

    @NotBlank(message = "Informe o modelo.")
    @Size(max = 80, message = "O modelo pode ter até 80 caracteres.")
    private String modelo;

    @NotNull(message = "Informe o ano de fabricação.")
    @Min(value = 1900, message = "O ano deve ser 1900 ou posterior.")
    private Integer anoFabricacao;

    @NotNull(message = "Informe o ano do modelo.")
    @Min(value = 1900, message = "O ano deve ser 1900 ou posterior.")
    private Integer anoModelo;

    @NotNull(message = "Informe a quilometragem.")
    @PositiveOrZero(message = "A quilometragem não pode ser negativa.")
    private Integer quilometragem;

    @NotBlank(message = "Informe a cor.")
    @Size(max = 40, message = "A cor pode ter até 40 caracteres.")
    private String cor;

    @NotBlank(message = "Informe o motor.")
    @Size(max = 60, message = "O motor pode ter até 60 caracteres.")
    private String motor;

    @NotNull(message = "Informe a potência.")
    @Positive(message = "A potência deve ser maior que zero.")
    private Integer potenciaCv;

    @NotNull(message = "Escolha o combustível.")
    private TipoCombustivel combustivel;

    @NotNull(message = "Escolha o câmbio.")
    private TipoCambio cambio;

    @NotNull(message = "Escolha o estado de conservação.")
    private EstadoConservacao conservacao;

    private TipoSelo selo;

    public static AnuncioForm de(Anuncio anuncio) {
        Veiculo veiculo = anuncio.getVeiculo();
        AnuncioForm form = new AnuncioForm();
        form.titulo = anuncio.getTitulo();
        form.descricao = anuncio.getDescricao();
        form.preco = anuncio.getPreco();
        form.selo = anuncio.getSelo();
        form.categoriaId = veiculo.getCategoria().getId();
        form.marca = veiculo.getMarca();
        form.modelo = veiculo.getModelo();
        form.anoFabricacao = veiculo.getAnoFabricacao();
        form.anoModelo = veiculo.getAnoModelo();
        form.quilometragem = veiculo.getQuilometragem();
        form.cor = veiculo.getCor();
        form.motor = veiculo.getMotor();
        form.potenciaCv = veiculo.getPotenciaCv();
        form.combustivel = veiculo.getCombustivel();
        form.cambio = veiculo.getCambio();
        form.conservacao = veiculo.getConservacao();
        return form;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public Integer getPotenciaCv() {
        return potenciaCv;
    }

    public void setPotenciaCv(Integer potenciaCv) {
        this.potenciaCv = potenciaCv;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public TipoCambio getCambio() {
        return cambio;
    }

    public void setCambio(TipoCambio cambio) {
        this.cambio = cambio;
    }

    public EstadoConservacao getConservacao() {
        return conservacao;
    }

    public void setConservacao(EstadoConservacao conservacao) {
        this.conservacao = conservacao;
    }

    public TipoSelo getSelo() {
        return selo;
    }

    public void setSelo(TipoSelo selo) {
        this.selo = selo;
    }
}
