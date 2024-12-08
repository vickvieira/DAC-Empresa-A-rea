package dto;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeroportos")
public class AeroportoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo", length = 3, nullable = false, unique = true)
    private String codigo; // Código de 3 letras

    @Column(name = "nome", length = 100, nullable = false)
    private String nome; // Nome do aeroporto

    @Column(name = "cidade", length = 100, nullable = false)
    private String cidade; // Cidade do aeroporto

    @Column(name = "estado", length = 50, nullable = false)
    private String estado; // Estado do aeroporto

    // Construtor padrão
    public AeroportoDTO() {}

    // Construtor com parâmetros
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
