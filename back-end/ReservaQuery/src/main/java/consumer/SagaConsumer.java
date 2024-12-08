package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import models.SagaReservaRequisition;
import service.ReservaService;
import dto.ReservaDTO;

@Component
public class SagaConsumer {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitmqConstantes.FILA_RESERVA)
    public void consumidor(SagaReservaRequisition requisition) {
        try {
            ReservaDTO reserva = reservaService.cadastrarReserva(requisition.getReserva());
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisition.getReserva());
        } catch (Exception e) {
            System.err.println("Erro ao processar reserva: " + e.getMessage());
        }
    }
}