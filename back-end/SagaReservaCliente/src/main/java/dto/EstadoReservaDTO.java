package dto;

import java.io.Serializable;

public class EstadoReservaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String codigoEstado;
    private String siglaEstado;
    private String descricaoEstado;

    public EstadoReservaDTO() {}

    public EstadoReservaDTO(String codigoEstado, String siglaEstado, String descricaoEstado) {
        this.codigoEstado = codigoEstado;
        this.siglaEstado = siglaEstado;
        this.descricaoEstado = descricaoEstado;
    }

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
