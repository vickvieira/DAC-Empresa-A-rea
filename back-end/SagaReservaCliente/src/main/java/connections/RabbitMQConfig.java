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
        // Criação das filas existentes
        Queue filaReserva = this.fila(RabbitmqConstantes.FILA_RESERVA);
        Queue filaVoo = this.fila(RabbitmqConstantes.FILA_VOO);
        Queue filaVooAtualizado = this.fila(RabbitmqConstantes.FILA_VOO_ATUALIZADO);
        Queue filaReservaAtualizada = this.fila(RabbitmqConstantes.FILA_RESERVA_ATUALIZADA);
        Queue filaClienteReserva = this.fila(RabbitmqConstantes.FILA_CLIENTE_RESERVA);

        Queue filaCancelaReserva = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA);
        Queue filaCancelaReservaAtualizada = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_ATUALIZADA);
        Queue filaVooCancela = this.fila(RabbitmqConstantes.VOO_CANCELA);
        Queue filaVooCancelaAtualizada = this.fila(RabbitmqConstantes.VOO_CANCELA_ATUALIZADA);

        Queue filaCancelaReservaVoo = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_VOO);
        Queue filaCancelaReservaVooAtualiza = this.fila(RabbitmqConstantes.FILA_CANCELA_RESERVA_VOO_ATUALIZA);
        Queue filaVooCancelaVoo = this.fila(RabbitmqConstantes.VOO_CANCELA_VOO);
        Queue filaVooCancelaVooAtualizada = this.fila(RabbitmqConstantes.VOO_CANCELA_voo_ATUALIZADA);
        Queue filaVariosClientes = this.fila(RabbitmqConstantes.FILA_VARIOS_CLIENTES);

        // Criação das novas filas
        Queue filaRealizaVoo = this.fila(RabbitmqConstantes.FILA_REALIZA_VOO);
        Queue filaRealizaVooAtualiza = this.fila(RabbitmqConstantes.FILA_REALIZA_VOO_ATUALIZA);
        Queue filaRealizaVooReserva = this.fila(RabbitmqConstantes.FILA_REALIZA_VOO_RESERVA);

        DirectExchange troca = this.trocaDireta();

        // Configuração das bindings existentes
        Binding ligacaoReserva = this.relacionamento(filaReserva, troca);
        Binding ligacaoVoo = this.relacionamento(filaVoo, troca);
        Binding ligacaoVooAtualizado = this.relacionamento(filaVooAtualizado, troca);
        Binding ligacaoReservaAtualizada = this.relacionamento(filaReservaAtualizada, troca);
        Binding ligacaoClienteReserva = this.relacionamento(filaClienteReserva, troca);

        Binding ligacaoCancelaReserva = this.relacionamento(filaCancelaReserva, troca);
        Binding ligacaoCancelaReservaAtualizada = this.relacionamento(filaCancelaReservaAtualizada, troca);
        Binding ligacaoVooCancela = this.relacionamento(filaVooCancela, troca);
        Binding ligacaoVooCancelaAtualizada = this.relacionamento(filaVooCancelaAtualizada, troca);

        Binding ligacaoCancelaReservaVoo = this.relacionamento(filaCancelaReservaVoo, troca);
        Binding ligacaoCancelaReservaVooAtualiza = this.relacionamento(filaCancelaReservaVooAtualiza, troca);
        Binding ligacaoVooCancelaVoo = this.relacionamento(filaVooCancelaVoo, troca);
        Binding ligacaoVooCancelaVooAtualizada = this.relacionamento(filaVooCancelaVooAtualizada, troca);
        Binding ligacaoVariosClientes = this.relacionamento(filaVariosClientes, troca);

        // Configuração das bindings para as novas filas
        Binding ligacaoRealizaVoo = this.relacionamento(filaRealizaVoo, troca);
        Binding ligacaoRealizaVooAtualiza = this.relacionamento(filaRealizaVooAtualiza, troca);
        Binding ligacaoRealizaVooReserva = this.relacionamento(filaRealizaVooReserva, troca);

        // Declarar as filas existentes
        this.amqpAdmin.declareQueue(filaReserva);
        this.amqpAdmin.declareQueue(filaVoo);
        this.amqpAdmin.declareQueue(filaVooAtualizado);
        this.amqpAdmin.declareQueue(filaReservaAtualizada);
        this.amqpAdmin.declareQueue(filaClienteReserva);

        this.amqpAdmin.declareQueue(filaCancelaReserva);
        this.amqpAdmin.declareQueue(filaCancelaReservaAtualizada);
        this.amqpAdmin.declareQueue(filaVooCancela);
        this.amqpAdmin.declareQueue(filaVooCancelaAtualizada);

        this.amqpAdmin.declareQueue(filaCancelaReservaVoo);
        this.amqpAdmin.declareQueue(filaCancelaReservaVooAtualiza);
        this.amqpAdmin.declareQueue(filaVooCancelaVoo);
        this.amqpAdmin.declareQueue(filaVooCancelaVooAtualizada);
        this.amqpAdmin.declareQueue(filaVariosClientes);

        // Declarar as novas filas
        this.amqpAdmin.declareQueue(filaRealizaVoo);
        this.amqpAdmin.declareQueue(filaRealizaVooAtualiza);
        this.amqpAdmin.declareQueue(filaRealizaVooReserva);

        // Declarar a exchange
        this.amqpAdmin.declareExchange(troca);

        // Declarar os bindings existentes
        this.amqpAdmin.declareBinding(ligacaoReserva);
        this.amqpAdmin.declareBinding(ligacaoVoo);
        this.amqpAdmin.declareBinding(ligacaoVooAtualizado);
        this.amqpAdmin.declareBinding(ligacaoReservaAtualizada);
        this.amqpAdmin.declareBinding(ligacaoClienteReserva);

        this.amqpAdmin.declareBinding(ligacaoCancelaReserva);
        this.amqpAdmin.declareBinding(ligacaoCancelaReservaAtualizada);
        this.amqpAdmin.declareBinding(ligacaoVooCancela);
        this.amqpAdmin.declareBinding(ligacaoVooCancelaAtualizada);

        this.amqpAdmin.declareBinding(ligacaoCancelaReservaVoo);
        this.amqpAdmin.declareBinding(ligacaoCancelaReservaVooAtualiza);
        this.amqpAdmin.declareBinding(ligacaoVooCancelaVoo);
        this.amqpAdmin.declareBinding(ligacaoVooCancelaVooAtualizada);
        this.amqpAdmin.declareBinding(ligacaoVariosClientes);

        // Declarar os bindings para as novas filas
        this.amqpAdmin.declareBinding(ligacaoRealizaVoo);
        this.amqpAdmin.declareBinding(ligacaoRealizaVooAtualiza);
        this.amqpAdmin.declareBinding(ligacaoRealizaVooReserva);
    }
}
