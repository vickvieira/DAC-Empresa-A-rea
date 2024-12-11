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
    private static final String NOME_EXCHANGE_CQRS = "CQRS";

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
        // SagaClienteReserva Exchange
        Queue filaReserva = this.fila(RabbitmqConstantes.FILA_RESERVA);
        Queue filaVoo = this.fila(RabbitmqConstantes.FILA_VOO);
        Queue filaVooAtualizadoSaga = this.fila(RabbitmqConstantes.FILA_VOO_ATUALIZADO);
        Queue filaReservaAtualizada = this.fila(RabbitmqConstantes.FILA_RESERVA_ATUALIZADA);

        // Cancelamento
        Queue filaCancelaReserva = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA);
        Queue filaCancelaReservaAtualizada = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_ATUALIZADA);
        Queue filaCancelaReservaVoo = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_VOO);
        Queue filaCancelaReservaVooAtualiza = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_VOO_ATUALIZA);

        DirectExchange trocaSaga = this.trocaDireta(NOME_EXCHANGE);

        Binding ligacaoReserva = this.relacionamento(filaReserva, trocaSaga);
        Binding ligacaoVoo = this.relacionamento(filaVoo, trocaSaga);
        Binding ligacaoVooAtualizadoSaga = this.relacionamento(filaVooAtualizadoSaga, trocaSaga);
        Binding ligacaoReservaAtualizada = this.relacionamento(filaReservaAtualizada, trocaSaga);

        // Cancelamento
        Binding ligacaoCancelaReserva = this.relacionamento(filaCancelaReserva, trocaSaga);
        Binding ligacaoCancelaReservaAtualizada = this.relacionamento(filaCancelaReservaAtualizada, trocaSaga);
        Binding ligacaoCancelaReservaVoo = this.relacionamento(filaCancelaReservaVoo, trocaSaga);
        Binding ligacaoCancelaReservaVooAtualiza = this.relacionamento(filaCancelaReservaVooAtualiza, trocaSaga);

        this.amqpAdmin.declareQueue(filaReserva);
        this.amqpAdmin.declareQueue(filaVoo);
        this.amqpAdmin.declareQueue(filaVooAtualizadoSaga);
        this.amqpAdmin.declareQueue(filaReservaAtualizada);

        this.amqpAdmin.declareQueue(filaCancelaReserva);
        this.amqpAdmin.declareQueue(filaCancelaReservaAtualizada);
        this.amqpAdmin.declareQueue(filaCancelaReservaVoo);
        this.amqpAdmin.declareQueue(filaCancelaReservaVooAtualiza);

        this.amqpAdmin.declareExchange(trocaSaga);

        this.amqpAdmin.declareBinding(ligacaoReserva);
        this.amqpAdmin.declareBinding(ligacaoVoo);
        this.amqpAdmin.declareBinding(ligacaoVooAtualizadoSaga);
        this.amqpAdmin.declareBinding(ligacaoReservaAtualizada);

        this.amqpAdmin.declareBinding(ligacaoCancelaReserva);
        this.amqpAdmin.declareBinding(ligacaoCancelaReservaAtualizada);
        this.amqpAdmin.declareBinding(ligacaoCancelaReservaVoo);
        this.amqpAdmin.declareBinding(ligacaoCancelaReservaVooAtualiza);

        // CQRS Exchange
        Queue filaAtualizaReservaQ = this.fila(RabbitmqConstantes.FILA_atualizaReservaQ);

        DirectExchange trocaCQRS = this.trocaDireta(NOME_EXCHANGE_CQRS);

        Binding ligacaoAtualizaReservaQ = this.relacionamento(filaAtualizaReservaQ, trocaCQRS);

        this.amqpAdmin.declareQueue(filaAtualizaReservaQ);

        this.amqpAdmin.declareExchange(trocaCQRS);

        this.amqpAdmin.declareBinding(ligacaoAtualizaReservaQ);
    }
}
