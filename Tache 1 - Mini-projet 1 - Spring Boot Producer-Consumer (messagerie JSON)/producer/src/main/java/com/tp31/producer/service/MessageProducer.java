package com.tp31.producer.service;

import com.tp31.producer.config.RabbitMQConfig;
import com.tp31.producer.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service pour publier des messages vers RabbitMQ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Publie un message JSON vers RabbitMQ
     * 
     * @param message le message à publier
     */
    public void sendMessage(MessageDto message) {
        log.info("📤 Publication du message: {}", message);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message);

        log.info("✅ Message publié avec succès vers l'exchange '{}'",
                RabbitMQConfig.EXCHANGE_NAME);
    }
}
