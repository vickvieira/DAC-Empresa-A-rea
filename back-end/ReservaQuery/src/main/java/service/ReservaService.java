package service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.EstadoReservaRepository;
import repository.HistoricoAlteracaoRepository;
import repository.ReservaRepository;
import dto.EstadoReservaDTO;
import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;

@Service
public class ReservaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private EstadoReservaRepository estadoReservaRepository;
    
    @Autowired
    private HistoricoAlteracaoRepository historicoAlteracaoRepository;

    public ReservaDTO cadastrarReserva(ReservaDTO reserva) {
        
        ReservaDTO reservaSalva = reservaRepository.save(reserva);
        return reservaSalva;
    }
    
    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);
    }

    public  EstadoReservaDTO cadastrarEstadoReserva(EstadoReservaDTO estadoReserva) {
        return estadoReservaRepository.save(estadoReserva);
    }
    
    
    public HistoricoAlteracaoDTO cadastrarHistoricoAlteracao(HistoricoAlteracaoDTO historico) {
        return historicoAlteracaoRepository.save(historico);
    }

    public List<HistoricoAlteracaoDTO> buscarHistoricoReservas(String codigoReserva) {
        return historicoAlteracaoRepository.findByCodigoReserva(codigoReserva);
    }
    
    public ReservaDTO buscarPorCodigoReserva(String codigoReserva) {
        ReservaDTO reserva = reservaRepository.findByCodigoReserva(codigoReserva);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada: " + codigoReserva);
        }
        return reserva;
    }

    public List<ReservaDTO> buscarReservasPorCliente(Long clienteId) {
        return reservaRepository.findByIdCliente(clienteId);
    }

}