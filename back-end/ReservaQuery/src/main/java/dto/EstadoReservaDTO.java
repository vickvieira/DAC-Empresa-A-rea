package dto;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "estado_reserva")
public class EstadoReservaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_estado", nullable = false, unique = true, length = 10)
    private String codigoEstado;

    @Column(name = "sigla_estado", nullable = false, unique = true, length = 5)
    private String siglaEstado;

    @Column(name = "descricao_estado", nullable = false, length = 100)
    private String descricaoEstado;

    // Construtor padrão
    public EstadoReservaDTO() {}

    // Construtor com parâmetros
    public EstadoReservaDTO(String codigoEstado, String siglaEstado, String descricaoEstado) {
        this.codigoEstado = codigoEstado;
        this.siglaEstado = siglaEstado;
        this.descricaoEstado = descricaoEstado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getDescricaoEstado() {
        return descricaoEstado;
    }

    public void setDescricaoEstado(String descricaoEstado) {
        this.descricaoEstado = descricaoEstado;
    }
}