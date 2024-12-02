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
            // Realiza o cadastro do usuário
            authService.cadastrarUsuario(user.getUserRequisitionDTO());
            
            // Configura o status de sucesso no objeto UserCliente
            user.setStatus("success");
            user.setMensagem("Usuário cadastrado com sucesso.");
        } catch (Exception e) {
            // Configura o status de erro no objeto UserCliente
            user.setStatus("error");
            user.setMensagem("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}