package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import service.ClienteService;
//import service.SagaService;

public class SagaConsumer {
	
	@Autowired
	private ClienteService clienteService;
	
    @RabbitListener(queues = RabbitmqConstantes.FILA_CLIENTE)
    private void consumidor(UserCliente user) throws Exception {
    	
    	
    }
}
