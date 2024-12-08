package dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class HistoricoAlteracaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String codigoReserva;

    private LocalDateTime dataHoraAlteracao;

    private EstadoReservaDTO estadoOrigem;

    private EstadoReservaDTO estadoDestino;

    public HistoricoAlteracaoDTO() {}

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