package models;

import java.io.Serializable;

public class ClienteMilhas implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idCliente;
    private double milhas;

    public ClienteMilhas(Long idCliente, double milhas) {
        this.idCliente = idCliente;
        this.milhas = milhas;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public double getMilhas() {
        return milhas;
    }

    public void setMilhas(double milhas) {
        this.milhas = milhas;
    }
}
