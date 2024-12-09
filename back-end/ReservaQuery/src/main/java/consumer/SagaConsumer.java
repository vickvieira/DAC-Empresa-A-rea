package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import models.CQRSModel;
import service.ReservaService;
import dto.EstadoReservaDTO;
import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;

@Component
public class SagaConsumer {

    @Autowired
    private ReservaService reservaService;

    @RabbitListener(queues = RabbitmqConstantes.FILA_atualizaReservaQ)
    public void consumidor(CQRSModel requisition) {
        try {
            System.out.println("Processando atualização da reserva");

            if (requisition.getReserva() != null) {
                ReservaDTO reserva = requisition.getReserva();
                reservaService.cadastrarReserva(reserva);
                System.out.println("Reserva processada: " + reserva.getCodigoReserva());
            }

            if (requisition.getEstadoReserva() != null) {
                EstadoReservaDTO estadoReserva = requisition.getEstadoReserva();
                reservaService.cadastrarEstadoReserva(estadoReserva);
                System.out.println("Estado da reserva atualizado: " + estadoReserva.getCodigoEstado());
            }

            if (requisition.getHistoricoAlteracoes() != null) {
                HistoricoAlteracaoDTO historico = requisition.getHistoricoAlteracoes();
                reservaService.cadastrarHistoricoAlteracao(historico);
                System.out.println("Histórico de alterações registrado: " + historico.getCodigoReserva());
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar atualização da reserva: " + e.getMessage());
        }
    }
}