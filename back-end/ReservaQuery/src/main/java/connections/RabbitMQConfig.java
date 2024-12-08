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
    private static final String NOME_EXCHANGE_ATUALIZARQUERY = "AtualizarQuery";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(String nomeExchange) {
        return new DirectExchange(nomeExchange);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(
            fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null
        );
    }

    @PostConstruct
    private void adicionaFilas() {
        // Fila e exchange para reserva
        Queue filaReserva = this.fila(RabbitmqConstantes.FILA_RESERVA);
        Queue filaVoo = this.fila(RabbitmqConstantes.FILA_VOO);

        DirectExchange trocaSaga = this.trocaDireta(NOME_EXCHANGE);

        Binding ligacaoReserva = this.relacionamento(filaReserva, trocaSaga);
        Binding ligacaoVoo = this.relacionamento(filaVoo, trocaSaga);

        this.amqpAdmin.declareQueue(filaReserva);
        this.amqpAdmin.declareQueue(filaVoo);
        this.amqpAdmin.declareExchange(trocaSaga);
        this.amqpAdmin.declareBinding(ligacaoReserva);
        this.amqpAdmin.declareBinding(ligacaoVoo);

        // Fila e exchange para atualizar query
        Queue filaAtualizaReservaQuery = this.fila(RabbitmqConstantes.FILA_atualizaReservaQ);

        DirectExchange trocaAtualizarQuery = this.trocaDireta(NOME_EXCHANGE_ATUALIZARQUERY);

        Binding ligacaoAtualizarQuery = this.relacionamento(filaAtualizaReservaQuery, trocaAtualizarQuery);

        this.amqpAdmin.declareQueue(filaAtualizaReservaQuery);
        this.amqpAdmin.declareExchange(trocaAtualizarQuery);
        this.amqpAdmin.declareBinding(ligacaoAtualizarQuery);
    }
}