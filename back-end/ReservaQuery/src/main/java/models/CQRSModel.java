package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import dto.EstadoReservaDTO;
import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;

public class CQRSModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private ReservaDTO reserva;
    private HistoricoAlteracaoDTO historicoAlteracoes;
    private EstadoReservaDTO estadoReserva;

    public CQRSModel() {}

    public CQRSModel(ReservaDTO reserva, HistoricoAlteracaoDTO historicoAlteracoes, EstadoReservaDTO estadoAtualReserva) {
        this.reserva = reserva;
        this.historicoAlteracoes = historicoAlteracoes;
        this.estadoReserva = estadoAtualReserva;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }

    public HistoricoAlteracaoDTO getHistoricoAlteracoes() {
        return historicoAlteracoes;
    }

    public void setHistoricoAlteracoes(HistoricoAlteracaoDTO historicoAlteracoes) {
        this.historicoAlteracoes = historicoAlteracoes;
    }

    public EstadoReservaDTO getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReservaDTO estadoAtualReserva) {
        this.estadoReserva = estadoAtualReserva;
    }
}