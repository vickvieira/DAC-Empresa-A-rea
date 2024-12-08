package dto;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(name = "estado_origem", nullable = false, length = 10)
    private String estadoOrigem; // Código do estado de origem

    @Column(name = "estado_destino", nullable = false, length = 10)
    private String estadoDestino; // Código do estado de destino

    public HistoricoAlteracaoDTO() {}

    public HistoricoAlteracaoDTO(String codigoReserva, LocalDateTime dataHoraAlteracao, String estadoOrigem, String estadoDestino) {
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

    public String getEstadoOrigem() {
        return estadoOrigem;
    }

    public void setEstadoOrigem(String estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }
}
