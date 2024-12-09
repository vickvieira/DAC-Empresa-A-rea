package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import models.SagaReservaRequisition;
import models.CQRSModel;
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
        } catch (Exception e) {
            System.err.println("Erro ao processar reserva: " + e.getMessage());
        }
    }
}