package models;

import dto.ReservaDTO;
import dto.VooDTO;
import java.io.Serializable;

public class SagaReservaRequisition implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCliente;
    private VooDTO voo;
    private ReservaDTO reserva;

    public SagaReservaRequisition() {}

    // Getters e Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public VooDTO getVoo() {
        return voo;
    }

    public void setVoo(VooDTO voo) {
        this.voo = voo;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }
}