package SagaService;

import DTO.UserRequisitionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SagaService {

    private final RabbitTemplate rabbitTemplate;

    public SagaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String iniciarSaga(UserRequisitionDTO userData) {
        try {
            // Envia mensagem para o exchange
            String response = (String) rabbitTemplate.convertSendAndReceive(
                "auth-service-exchange", 
                "auth-service-queue", 
                userData
            );
            if ("SUCCESS".equals(response)) {
                return "Usuário cadastrado com sucesso!";
            }
            return "Erro ao cadastrar usuário.";
        } catch (Exception e) {
            return "Erro ao enviar mensagem: " + e.getMessage();
        }
    }
}