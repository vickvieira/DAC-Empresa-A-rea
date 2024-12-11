package dto;

import java.io.Serializable;
import jakarta.persistence.*;

public class FuncionarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private String telefone;

    private String tipo;

    private String ruaNumero;

    private String complemento;

    private String cep;

    private String cidade;

    private String estado;

    private Double milhas;

    public FuncionarioDTO() {}

    public FuncionarioDTO(String nome, String email, String cpf, String telefone, String tipo, String ruaNumero,
                       String complemento, String cep, String cidade, String estado, Double milhas) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tipo = tipo;
        this.ruaNumero = ruaNumero;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.milhas = milhas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuaNumero() {
        return ruaNumero;
    }

    public void setRuaNumero(String ruaNumero) {
        this.ruaNumero = ruaNumero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMilhas() {
        return milhas;
    }

    public void setMilhas(Double milhas) {
        this.milhas = milhas;
    }
}