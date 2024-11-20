package SagaConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaConfig {

    @Bean
    public Queue authQueue() {
        return new Queue("auth-service-queue", true);
    }

    @Bean
    public Queue clientesQueue() {
        return new Queue("clientes-service-queue", true);
    }
}