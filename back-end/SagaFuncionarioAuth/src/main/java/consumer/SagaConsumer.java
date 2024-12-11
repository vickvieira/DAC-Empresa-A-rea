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
	
	@RabbitListener(queues = RabbitmqConstantes.FILA_CADASTRO_FUNC_ATUALIZADO)
	private void consumidor(UserCliente user) {
	    try {
	        System.out.print("Saga cliente cadastrado");
	        this.sagaService.enviaMensagem(RabbitmqConstantes.FILA_CADASTRO, user.getUserRequisitionDTO());
	        System.out.print("Cliente cadastrado");
	    } catch (Exception e) {
	        System.err.println("Erro ao processar mensagem: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
