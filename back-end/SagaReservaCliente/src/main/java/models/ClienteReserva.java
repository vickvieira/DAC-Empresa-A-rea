package models;
import java.io.Serializable;

public class ClienteReserva implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String evento;
    private Long idCliente;
    private Double milhas;

    public ClienteReserva(String evento, Long long1, Double milhas) {
        this.evento = evento;
        this.idCliente = long1;
        this.milhas = milhas;
    }

    // Getters e Setters
    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getMilhas() {
        return milhas;
    }

    public void setMilhas(Double milhas) {
        this.milhas = milhas;
    }
}