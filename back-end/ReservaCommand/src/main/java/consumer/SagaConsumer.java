package consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import models.SagaReservaRequisition;
import models.CQRSModel;
import service.ReservaService;
import dto.ReservaDTO;
import dto.EstadoReservaDTO;
import dto.HistoricoAlteracaoDTO;
import dto.HistoricoAlteracaoDTO;

@Component
public class SagaConsumer {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitmqConstantes.FILA_RESERVA)
    public void consumidor(SagaReservaRequisition requisition) {
        try {
            ReservaDTO reservaNova = requisition.getReserva();
            if (reservaNova == null) {
                throw new IllegalArgumentException("Reserva não pode ser nula.");
            }

            reservaNova.setIdCliente(requisition.getIdCliente());

            if (reservaNova.getIdCliente() == null) {
                throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
            }
            
            ReservaDTO reserva = reservaService.cadastrarReserva(requisition.getReserva());
            CQRSModel requisitionCQRS = new CQRSModel();
            requisitionCQRS.setReserva(reserva);
            System.out.print("Rserva processada no Command");
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisitionCQRS);
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_RESERVA_ATUALIZADA, requisition);
        } catch (Exception e) {
            System.err.println("Erro ao processar reserva: " + e.getMessage());
        }
    }
    
    @RabbitListener(queues = RabbitmqConstantes.FILA_CANCELA_RESERVA)
    public void consumidorCancelarReserva(SagaReservaRequisition requisition) {
        try {
            ReservaDTO reservaCancelada = requisition.getReserva();
            if (reservaCancelada == null || reservaCancelada.getCodigoReserva() == null) {
                throw new IllegalArgumentException("Reserva ou código da reserva não pode ser nulo.");
            }

            ReservaDTO reserva = reservaService.buscarPorCodigoReserva(reservaCancelada.getCodigoReserva());
            if (reserva == null) {
                throw new IllegalArgumentException("Reserva não encontrada: " + reservaCancelada.getCodigoReserva());
            }
            
            if (reserva.getEstadoReserva() == "CRES") {
                throw new IllegalArgumentException("Reserva já cancelada: " + reservaCancelada.getCodigoReserva());
            }

            reserva.setEstadoReserva("CRES");
            String estadoOrigem = reserva.getEstadoReserva();
         
            HistoricoAlteracaoDTO hist = new HistoricoAlteracaoDTO();
            hist.setCodigoReserva(reservaCancelada.getCodigoReserva());
            hist.setDataHoraAlteracao(LocalDateTime.now());
            hist.setEstadoOrigem(estadoOrigem);
            hist.setEstadoDestino("CRES");

            reservaService.atualizarReserva(reserva);
            reservaService.insereHistorico(hist);

            CQRSModel requisitionCQRS = new CQRSModel();
            requisitionCQRS.setReserva(reserva);
            requisitionCQRS.setHistoricoAlteracoes(hist);
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisitionCQRS);
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_CANCELA_RESERVA_ATUALIZADA, requisition);
            System.out.println("Reserva cancelada com sucesso: " + reserva.getCodigoReserva());
        } catch (Exception e) {
            System.err.println("Erro ao processar cancelamento de reserva: " + e.getMessage());
        }
    }
}