package consumer;

import constantes.RabbitmqConstantes;
import dto.UserCliente;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import service.AuthService;

@Component
public class UsuarioConsumer {
	
	@Autowired
    private AuthService authService;
	
    @RabbitListener(queues = RabbitmqConstantes.FILA_CADASTRO)
    public void consumidor(UserCliente user) {
        try {
            authService.cadastrarUsuario(user.getUserRequisitionDTO());
        } catch (Exception e) {
        	authService.enviaMensagem(RabbitmqConstantes.FILA_ROLLBACK, user);
        	System.out.print(e.getMessage());
        }
    }
}