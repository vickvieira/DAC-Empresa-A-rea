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

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
        System.out.println("AmqpAdmin initialized: " + (amqpAdmin != null));
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
        System.out.println("PostConstruct method called");
        
        Queue filaCadastro = this.fila(RabbitmqConstantes.FILA_CADASTRO);
        Queue filaCliente = this.fila(RabbitmqConstantes.FILA_CLIENTE);
        Queue filaCadastrado = this.fila(RabbitmqConstantes.FILA_CLIENTE_CADASTRADO);
        Queue filaEmail = this.fila(RabbitmqConstantes.FILA_ENVIAR_EMAIL);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoUsuario = this.relacionamento(filaCadastro, troca);
        Binding ligacaoCliente = this.relacionamento(filaCliente, troca);
        Binding ligacaoCadastrado = this.relacionamento(filaCadastrado, troca);
        Binding ligacaoEmail = this.relacionamento(filaEmail, troca);

        this.amqpAdmin.declareQueue(filaCadastro);
        this.amqpAdmin.declareQueue(filaCliente);
        this.amqpAdmin.declareQueue(filaCadastrado);
        this.amqpAdmin.declareQueue(filaEmail);
        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoUsuario);
        this.amqpAdmin.declareBinding(ligacaoCliente);
        this.amqpAdmin.declareBinding(ligacaoCadastrado);
        this.amqpAdmin.declareBinding(ligacaoEmail);
    }
}