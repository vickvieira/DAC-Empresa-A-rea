package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import service.SagaService;

@Component
public class SagaConsumer {
	
	@Autowired
	private SagaService sagaService;
	
    @RabbitListener(queues = RabbitmqConstantes.FILA_CLIENTE_CADASTRADO)
    private void consumidor(UserCliente user) throws Exception {
    	this.sagaService.enviaMensagem(RabbitmqConstantes.FILA_CLIENTE, user);
    	System.out.print("Cliente cadastrado");
    }
}
