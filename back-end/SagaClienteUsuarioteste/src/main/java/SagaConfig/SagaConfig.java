package SagaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class SagaConfig {

    public static final String EXCHANGE_NAME = "saga.exchange";
    public static final String ROUTING_KEY = "saga.routingKey";

    @Bean
    public Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
}