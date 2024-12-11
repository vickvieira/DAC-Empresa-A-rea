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
    private static final String NOME_EXCHANGE = "SagaClienteUsuario";
    private static final String NOME_EXCHANGE_RESERVA = "SagaClienteReserva";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(
            fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null
        );
    }

    @PostConstruct
    private void adicionaFilas() {
        // Exchange SagaClienteUsuario
        Queue filaCadastro = this.fila(RabbitmqConstantes.FILA_CADASTRO);
        Queue filaCliente = this.fila(RabbitmqConstantes.FILA_CLIENTE);
        Queue filaCadastrado = this.fila(RabbitmqConstantes.FILA_CLIENTE_CADASTRADO);
        Queue filaEmail = this.fila(RabbitmqConstantes.FILA_ENVIAR_EMAIL);
        Queue filaRollback = this.fila(RabbitmqConstantes.FILA_ROLLBACK);

        DirectExchange trocaUsuario = this.trocaDireta(NOME_EXCHANGE);

        Binding ligacaoUsuario = this.relacionamento(filaCadastro, trocaUsuario);
        Binding ligacaoCliente = this.relacionamento(filaCliente, trocaUsuario);
        Binding ligacaoCadastrado = this.relacionamento(filaCadastrado, trocaUsuario);
        Binding ligacaoEmail = this.relacionamento(filaEmail, trocaUsuario);
        Binding ligacaoRollback = this.relacionamento(filaRollback, trocaUsuario);

        this.amqpAdmin.declareQueue(filaCadastro);
        this.amqpAdmin.declareQueue(filaCliente);
        this.amqpAdmin.declareQueue(filaCadastrado);
        this.amqpAdmin.declareQueue(filaEmail);
        this.amqpAdmin.declareQueue(filaRollback);
        this.amqpAdmin.declareExchange(trocaUsuario);

        this.amqpAdmin.declareBinding(ligacaoUsuario);
        this.amqpAdmin.declareBinding(ligacaoCliente);
        this.amqpAdmin.declareBinding(ligacaoCadastrado);
        this.amqpAdmin.declareBinding(ligacaoEmail);
        this.amqpAdmin.declareBinding(ligacaoRollback);

        // Exchange SagaClienteReserva
        Queue filaClienteReserva = this.fila(RabbitmqConstantes.FILA_CLIENTE_RESERVA);

        DirectExchange trocaReserva = this.trocaDireta(NOME_EXCHANGE_RESERVA);

        Binding ligacaoClienteReserva = this.relacionamento(filaClienteReserva, trocaReserva);

        this.amqpAdmin.declareQueue(filaClienteReserva);
        this.amqpAdmin.declareExchange(trocaReserva);

        this.amqpAdmin.declareBinding(ligacaoClienteReserva);
    }
}
