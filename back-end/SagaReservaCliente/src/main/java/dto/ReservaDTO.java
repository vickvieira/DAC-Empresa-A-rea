package dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ReservaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String codigoReserva;
    private String codigoVoo;
    private LocalDateTime dataHoraReserva;
    private EstadoReservaDTO estadoReserva;
    private double valorPago;
    private int milhasGastas;
    
    
    // Construtor padrão
    public ReservaDTO() {}

    // Construtor com parâmetros
    public ReservaDTO(String codigoReserva, String codigoVoo, LocalDateTime dataHoraReserva, EstadoReservaDTO estadoReserva) {
        this.codigoReserva = codigoReserva;
        this.codigoVoo = codigoVoo;
        this.dataHoraReserva = dataHoraReserva;
        this.estadoReserva = estadoReserva;
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

    public EstadoReservaDTO getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReservaDTO estadoReserva) {
        this.estadoReserva = estadoReserva;
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
    
    
}
