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
        // Criar as filas especificadas
        Queue filaCadastro = this.fila(RabbitmqConstantes.FILA_CADASTRO);
        Queue filaCadastroFunc = this.fila(RabbitmqConstantes.FILA_CADASTRO_FUNC);
        Queue filaCadastroFuncAtualizado = this.fila(RabbitmqConstantes.FILA_CADASTRO_FUNC_ATUALIZADO);
        Queue filaEnviarEmail = this.fila(RabbitmqConstantes.FILA_ENVIAR_EMAIL);

        // Criar a exchange
        DirectExchange troca = this.trocaDireta();

        // Criar os bindings
        Binding ligacaoCadastro = this.relacionamento(filaCadastro, troca);
        Binding ligacaoCadastroFunc = this.relacionamento(filaCadastroFunc, troca);
        Binding ligacaoCadastroFuncAtualizado = this.relacionamento(filaCadastroFuncAtualizado, troca);
        Binding ligacaoEnviarEmail = this.relacionamento(filaEnviarEmail, troca);

        // Declarar as filas
        this.amqpAdmin.declareQueue(filaCadastro);
        this.amqpAdmin.declareQueue(filaCadastroFunc);
        this.amqpAdmin.declareQueue(filaCadastroFuncAtualizado);
        this.amqpAdmin.declareQueue(filaEnviarEmail);

        // Declarar a exchange
        this.amqpAdmin.declareExchange(troca);

        // Declarar os bindings
        this.amqpAdmin.declareBinding(ligacaoCadastro);
        this.amqpAdmin.declareBinding(ligacaoCadastroFunc);
        this.amqpAdmin.declareBinding(ligacaoCadastroFuncAtualizado);
        this.amqpAdmin.declareBinding(ligacaoEnviarEmail);
    }
}
