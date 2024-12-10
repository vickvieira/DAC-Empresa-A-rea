package dto;

import java.io.Serializable;

public class AeroportoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;
    private String cidade;
    private String estado;

    public AeroportoDTO() {}

    public AeroportoDTO(String codigo, String nome, String cidade, String estado) {
        this.codigo = codigo;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
