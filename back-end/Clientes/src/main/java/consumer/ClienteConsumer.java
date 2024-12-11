package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import models.ClienteReserva;
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
            clienteService.enviaMensagem(RabbitmqConstantes.FILA_CLIENTE_CADASTRADO, user);
            System.out.println("mensagem enviada");
        } catch (IllegalArgumentException e) {
        	System.err.println("Erro cliente: já existente " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    @RabbitListener(queues = RabbitmqConstantes.FILA_CLIENTE_RESERVA)
    public void consumidor(ClienteReserva clienteReserva) {
        try {
            System.out.println("Recebendo mensagem para o cliente: " + clienteReserva.getIdCliente());
            clienteService.adicionarMilhasERegistrarEvento(clienteReserva);
            System.out.println("Milhas adicionadas e evento registrado com sucesso para o cliente: " + clienteReserva.getIdCliente());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar mensagem: " + e.getMessage());
        }
    }
}
