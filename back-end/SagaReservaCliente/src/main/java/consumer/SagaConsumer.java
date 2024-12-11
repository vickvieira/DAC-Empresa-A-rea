package consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import models.ClienteReserva;
import models.SagaReservaRequisition;

@Component
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
	    
	    @RabbitListener(queues = RabbitmqConstantes.FILA_RESERVA_ATUALIZADA)
	    public void consumidorReserva(SagaReservaRequisition requisition) {
	        try {
	            System.out.println("Processando reserva: " + requisition.getReserva().getCodigoReserva());

	            ClienteReserva clienteReserva = new ClienteReserva(
	                "Reserva realizada",
	                requisition.getIdCliente(),
	                requisition.getReserva().getMilhasGastas() * -1
	            );

	            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_CLIENTE_RESERVA, clienteReserva);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar reserva: " + e.getMessage());
	        }
	    }
	    
	    
	    @RabbitListener(queues = RabbitmqConstantes.VOO_CANCELA_ATUALIZADA)
	    public void consumidorVooCancelado(SagaReservaRequisition requisition) {
	        try {
	        	System.out.print("Processando cancelamento de reserva"+requisition.getReserva().getCodigoReserva());
	            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_CANCELA_RESERVA, requisition);

	        } catch (Exception e) {
	            System.err.println("Erro ao cancelar  reserva: " + e.getMessage());
	        }
	    }
	    
	    @RabbitListener(queues = RabbitmqConstantes.FILA_CANCELA_RESERVA_ATUALIZADA)
	    public void consumidorReservaCamceçada(SagaReservaRequisition requisition) {
	        try {
	            System.out.println("Processando reserva: " + requisition.getReserva().getCodigoReserva());

	            ClienteReserva clienteReserva = new ClienteReserva(
	                "Reserva realizada",
	                requisition.getIdCliente(),
	                requisition.getReserva().getMilhasGastas()
	            );

	            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_CLIENTE_RESERVA, clienteReserva);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar reserva: " + e.getMessage());
	        }
	    }
	    
}
