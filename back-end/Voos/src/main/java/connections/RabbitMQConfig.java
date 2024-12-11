package connections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import constantes.RabbitmqConstantes;
import jakarta.annotation.PostConstruct;

@Component
public class RabbitMQConfig {
    private static final String NOME_EXCHANGE = "SagaClienteReserva";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(
            fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null
        );
    }

    @PostConstruct
    private void adicionaFilas() {
        // Filas existentes
        Queue filaReserva = this.fila(RabbitmqConstantes.FILA_RESERVA);
        Queue filaVoo = this.fila(RabbitmqConstantes.FILA_VOO);
        Queue filaVooAtualizado = this.fila(RabbitmqConstantes.FILA_VOO_ATUALIZADO);

        // Novas filas
        Queue filaVooCancelaVoo = this.fila(RabbitmqConstantes.VOO_CANCELA_VOO);
        Queue filaVooCancelaVooAtualizada = this.fila(RabbitmqConstantes.VOO_CANCELA_voo_ATUALIZADA);

        DirectExchange troca = this.trocaDireta();

        // Bindings para filas existentes
        Binding ligacaoReserva = this.relacionamento(filaReserva, troca);
        Binding ligacaoVoo = this.relacionamento(filaVoo, troca);
        Binding ligacaoVooAtualizado = this.relacionamento(filaVooAtualizado, troca);

        // Bindings para novas filas
        Binding ligacaoVooCancelaVoo = this.relacionamento(filaVooCancelaVoo, troca);
        Binding ligacaoVooCancelaVooAtualizada = this.relacionamento(filaVooCancelaVooAtualizada, troca);

        // Declaração de filas existentes
        this.amqpAdmin.declareQueue(filaReserva);
        this.amqpAdmin.declareQueue(filaVoo);
        this.amqpAdmin.declareQueue(filaVooAtualizado);

        // Declaração de novas filas
        this.amqpAdmin.declareQueue(filaVooCancelaVoo);
        this.amqpAdmin.declareQueue(filaVooCancelaVooAtualizada);

        // Declaração de exchange
        this.amqpAdmin.declareExchange(troca);

        // Declaração de bindings para filas existentes
        this.amqpAdmin.declareBinding(ligacaoReserva);
        this.amqpAdmin.declareBinding(ligacaoVoo);
        this.amqpAdmin.declareBinding(ligacaoVooAtualizado);

        // Declaração de bindings para novas filas
        this.amqpAdmin.declareBinding(ligacaoVooCancelaVoo);
        this.amqpAdmin.declareBinding(ligacaoVooCancelaVooAtualizada);
    }
}
