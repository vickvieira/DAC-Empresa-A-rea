package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import service.FuncService;
//import service.SagaService;

@Component
public class FuncConsumer {
	
	@Autowired
	private FuncService clienteService;
    @RabbitListener(queues = RabbitmqConstantes.FILA_CADASTRO_FUNC)
    private void consumidor(UserCliente user) {
        try {
            clienteService.cadastrarCliente(user.getClienteDTO());
            System.out.println("Cliente Func com sucesso: " + user.getClienteDTO().getCpf());
            clienteService.enviaMensagem(RabbitmqConstantes.FILA_CADASTRO_FUNC_ATUALIZADO, user);
            System.out.println("mensagem enviada");
        } catch (IllegalArgumentException e) {
        	System.err.println("Erro cliente: já existente " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao cadastrar cliente: " + e.getMessage());
        }
    }
    
}
