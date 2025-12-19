package com.tp31.producer.service;

import com.tp31.producer.config.RabbitMQConfig;
import com.tp31.producer.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service pour publier des Users vers RabbitMQ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Publie un User JSON vers RabbitMQ pour persistance MySQL
     * 
     * @param user le user à publier
     */
    public void publishUser(UserDto user) {
        log.info("📤 Publication du User: {}", user);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                user);

        log.info("✅ User publié avec succès vers l'exchange '{}'",
                RabbitMQConfig.EXCHANGE_NAME);
        log.info("🎯 Le Consumer va persister cet utilisateur dans MySQL");
    }
}
