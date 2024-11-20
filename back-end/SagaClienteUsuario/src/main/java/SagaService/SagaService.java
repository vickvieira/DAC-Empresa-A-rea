package SagaService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import DTO.UserRequisitionDTO;

@Service
public class SagaService {

    private final RabbitTemplate rabbitTemplate;

    public SagaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String iniciarSaga(UserRequisitionDTO userData) {
        // Enviar dados para Auth Service
        String authResponse = (String) rabbitTemplate.convertSendAndReceive("auth-service-queue", userData.getUsuarioDTO());
        if (!"SUCCESS".equals(authResponse)) {
            return "Erro ao cadastrar login!";
        }

        // Enviar dados para Cliente Service
        String clienteResponse = (String) rabbitTemplate.convertSendAndReceive("clientes-service-queue", userData.getClienteDTO());
        if (!"SUCCESS".equals(clienteResponse)) {
            return "Erro ao cadastrar cliente!";
        }

        return "Cadastro realizado com sucesso!";
    }
}