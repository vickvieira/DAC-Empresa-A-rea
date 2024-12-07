package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import service.ClienteService;
//import service.SagaService;

@Component
public class ClienteConsumer {
	
	@Autowired
	private ClienteService clienteService;
	
    @RabbitListener(queues = RabbitmqConstantes.FILA_CLIENTE)
    private void consumidor(UserCliente user) {
        try {
            clienteService.cadastrarCliente(user.getClienteDTO());
            System.out.println("Cliente cadastrado com sucesso: " + user.getClienteDTO().getCpf());
        } catch (IllegalArgumentException e) {
            clienteService.enviaMensagem(RabbitmqConstantes.FILA_ROLLBACK, user);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao cadastrar cliente: " + e.getMessage());
        }
    }
}
