package dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class ReservaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_reserva", nullable = false, unique = true, length = 10)
    private String codigoReserva;

    @Column(name = "codigo_voo", nullable = false, length = 10)
    private String codigoVoo;

    @Column(name = "data_hora_reserva", nullable = false)
    private LocalDateTime dataHoraReserva;

    @Column(name = "valorPago")
    private double valorPago;
    
    @Column(name = "milhasGastas")
    private int milhasGastas;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_reserva_id", nullable = false)
    private EstadoReservaDTO estadoReserva;
    
    

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
