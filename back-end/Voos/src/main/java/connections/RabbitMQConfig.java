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
        Queue filaReserva = this.fila(RabbitmqConstantes.FILA_RESERVA);
        Queue filaVoo = this.fila(RabbitmqConstantes.FILA_VOO);
        Queue filaVooAtualizado = this.fila(RabbitmqConstantes.FILA_VOO_ATUALIZADO);

        Queue filaRealizaVoo = this.fila(RabbitmqConstantes.FILA_REALIZA_VOO);
        Queue filaRealizaVooAtualiza = this.fila(RabbitmqConstantes.FILA_REALIZA_VOO_ATUALIZA);

        Queue filaVooCancelaVoo = this.fila(RabbitmqConstantes.VOO_CANCELA_VOO);
        Queue filaVooCancelaVooAtualizada = this.fila(RabbitmqConstantes.VOO_CANCELA_voo_ATUALIZADA);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoReserva = this.relacionamento(filaReserva, troca);
        Binding ligacaoVoo = this.relacionamento(filaVoo, troca);
        Binding ligacaoVooAtualizado = this.relacionamento(filaVooAtualizado, troca);

        Binding ligacaoRealizaVoo = this.relacionamento(filaRealizaVoo, troca);
        Binding ligacaoRealizaVooAtualiza = this.relacionamento(filaRealizaVooAtualiza, troca);

        Binding ligacaoVooCancelaVoo = this.relacionamento(filaVooCancelaVoo, troca);
        Binding ligacaoVooCancelaVooAtualizada = this.relacionamento(filaVooCancelaVooAtualizada, troca);

        this.amqpAdmin.declareQueue(filaReserva);
        this.amqpAdmin.declareQueue(filaVoo);
        this.amqpAdmin.declareQueue(filaVooAtualizado);

        this.amqpAdmin.declareQueue(filaRealizaVoo);
        this.amqpAdmin.declareQueue(filaRealizaVooAtualiza);

        this.amqpAdmin.declareQueue(filaVooCancelaVoo);
        this.amqpAdmin.declareQueue(filaVooCancelaVooAtualizada);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoReserva);
        this.amqpAdmin.declareBinding(ligacaoVoo);
        this.amqpAdmin.declareBinding(ligacaoVooAtualizado);

        this.amqpAdmin.declareBinding(ligacaoRealizaVoo);
        this.amqpAdmin.declareBinding(ligacaoRealizaVooAtualiza);

        this.amqpAdmin.declareBinding(ligacaoVooCancelaVoo);
        this.amqpAdmin.declareBinding(ligacaoVooCancelaVooAtualizada);
    }
}
