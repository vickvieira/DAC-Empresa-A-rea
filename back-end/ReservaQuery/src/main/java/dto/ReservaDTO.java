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
    
    @Column(name = "valor_pago")
    private double valorPago;

    @Column(name = "milhas_gastas")
    private double milhasGastas;

    @Column(name = "quantidade_poltronas_reservadas", nullable = false)
    private int quantidadePoltronasReservadas;

    @Column(name = "estado_reserva", nullable = false, length = 10)
    private String estadoReserva;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    public ReservaDTO() {}

    public ReservaDTO(String codigoReserva, String codigoVoo, LocalDateTime dataHoraReserva, String estadoReserva, int quantidadePoltronasReservadas, double valorPago, double milhasGastas, Long idCliente) {
        this.codigoReserva = codigoReserva;
        this.codigoVoo = codigoVoo;
        this.dataHoraReserva = dataHoraReserva;
        this.estadoReserva = estadoReserva;
        this.quantidadePoltronasReservadas = quantidadePoltronasReservadas;
        this.valorPago = valorPago;
        this.milhasGastas = milhasGastas;
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

    public double getMilhasGastas() {
        return milhasGastas;
    }

    public void setMilhasGastas(double milhasGastas) {
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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long clienteId) {
        this.idCliente = clienteId;
    }
}
