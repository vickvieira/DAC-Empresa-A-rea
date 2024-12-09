package dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ReservaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String codigoReserva;
    private String codigoVoo;
    private LocalDateTime dataHoraReserva;
    private double valorPago;
    private int milhasGastas;
    private int quantidadePoltronasReservadas;
    private String estadoReserva;
    private Long idCliente;

    public ReservaDTO() {}

    public ReservaDTO(String codigoReserva, String codigoVoo, LocalDateTime dataHoraReserva, String estadoReserva, int quantidadePoltronasReservadas, Long idCliente) {
        this.codigoReserva = codigoReserva;
        this.codigoVoo = codigoVoo;
        this.dataHoraReserva = dataHoraReserva;
        this.estadoReserva = estadoReserva;
        this.quantidadePoltronasReservadas = quantidadePoltronasReservadas;
        this.idCliente = idCliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getCodigoVoo() {
        return codigoVoo;
    }

    public void setCodigoVoo(String codigoVoo) {
        this.codigoVoo = codigoVoo;
    }

    public LocalDateTime getDataHoraReserva() {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(LocalDateTime dataHoraReserva) {
        this.dataHoraReserva = dataHoraReserva;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public int getMilhasGastas() {
        return milhasGastas;
    }

    public void setMilhasGastas(int milhasGastas) {
        this.milhasGastas = milhasGastas;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public int getQuantidadePoltronasReservadas() {
        return quantidadePoltronasReservadas;
    }

    public void setQuantidadePoltronasReservadas(int quantidadePoltronasReservadas) {
        this.quantidadePoltronasReservadas = quantidadePoltronasReservadas;
    }

    public Long getClienteId() {
        return idCliente;
    }

    public void setClienteId(Long clienteId) {
        this.idCliente = clienteId;
    }
}
