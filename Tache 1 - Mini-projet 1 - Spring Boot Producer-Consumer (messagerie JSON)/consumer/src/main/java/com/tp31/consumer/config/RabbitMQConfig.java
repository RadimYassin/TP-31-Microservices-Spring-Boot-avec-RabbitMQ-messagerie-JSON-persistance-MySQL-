package com.tp31.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration RabbitMQ pour le Consumer
 * Déclare les mêmes ressources que le Producer
 */
@Configuration
public class RabbitMQConfig {

    // Constantes identiques au Producer
    public static final String EXCHANGE_NAME = "user_exchange";
    public static final String QUEUE_NAME = "user_queue";
    public static final String ROUTING_KEY = "user_routing_key";

    /**
     * Déclare l'exchange (même si déjà créé par le Producer)
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * Déclare la queue (même si déjà créée par le Producer)
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    /**
     * Crée le binding
     */
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    /**
     * Configure Jackson2JsonMessageConverter pour désérialiser le JSON
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
