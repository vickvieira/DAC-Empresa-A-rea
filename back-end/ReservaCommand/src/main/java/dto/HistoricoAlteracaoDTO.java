package dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "historico_alteracao")
public class HistoricoAlteracaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_reserva", nullable = false, length = 10)
    private String codigoReserva;

    @Column(name = "data_hora_alteracao", nullable = false)
    private LocalDateTime dataHoraAlteracao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_origem_id", nullable = false)
    private EstadoReservaDTO estadoOrigem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_destino_id", nullable = false)
    private EstadoReservaDTO estadoDestino;

    // Construtor padrão
    public HistoricoAlteracaoDTO() {}

    // Construtor com parâmetros
    public HistoricoAlteracaoDTO(String codigoReserva, LocalDateTime dataHoraAlteracao, EstadoReservaDTO estadoOrigem, EstadoReservaDTO estadoDestino) {
        this.codigoReserva = codigoReserva;
        this.dataHoraAlteracao = dataHoraAlteracao;
        this.estadoOrigem = estadoOrigem;
        this.estadoDestino = estadoDestino;
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

    public LocalDateTime getDataHoraAlteracao() {
        return dataHoraAlteracao;
    }

    public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
        this.dataHoraAlteracao = dataHoraAlteracao;
    }

    public EstadoReservaDTO getEstadoOrigem() {
        return estadoOrigem;
    }

    public void setEstadoOrigem(EstadoReservaDTO estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    public EstadoReservaDTO getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(EstadoReservaDTO estadoDestino) {
        this.estadoDestino = estadoDestino;
    }
}