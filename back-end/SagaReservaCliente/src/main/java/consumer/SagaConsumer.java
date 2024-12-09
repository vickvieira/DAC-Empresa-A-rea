package consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import constantes.RabbitmqConstantes;
import dto.VooDTO;
import models.SagaReservaRequisition;


public class SagaConsumer  {


	    @Autowired
	    private RabbitTemplate rabbitTemplate;

	    @RabbitListener(queues = RabbitmqConstantes.FILA_VOO_ATUALIZADO)
	    public void consumidor(SagaReservaRequisition requisition) {
	        try {
	        	System.out.print("Processando reserva"+requisition.getReserva().getCodigoReserva());
	            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_RESERVA, requisition);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar reserva: " + e.getMessage());
	        }
	    }
}
