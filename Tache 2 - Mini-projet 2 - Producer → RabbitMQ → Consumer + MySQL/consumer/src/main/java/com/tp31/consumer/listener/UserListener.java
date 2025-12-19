package com.tp31.consumer.listener;

import com.tp31.consumer.config.RabbitMQConfig;
import com.tp31.consumer.dto.UserDto;
import com.tp31.consumer.entity.User;
import com.tp31.consumer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener pour consommer les Users de RabbitMQ et les persister dans MySQL
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {

    private final UserService userService;

    /**
     * Méthode appelée automatiquement lors de la réception d'un User
     * Le message JSON est automatiquement désérialisé en UserDto
     * puis persisté dans MySQL
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveUser(UserDto userDto) {
        log.info("========================================");
        log.info("📨 USER REÇU DE RABBITMQ !");
        log.info("========================================");
        log.info("👤 Nom: {}", userDto.getName());
        log.info("📧 Email: {}", userDto.getEmail());
        log.info("🎂 Âge: {}", userDto.getAge());
        log.info("🕐 Créé le: {}", userDto.getCreatedAt());
        log.info("========================================");

        try {
            // Persister le User dans MySQL
            User savedUser = userService.saveUser(userDto);

            log.info("========================================");
            log.info("💾 PERSISTANCE RÉUSSIE !");
            log.info("========================================");
            log.info("🆔 ID en base: {}", savedUser.getId());
            log.info("👤 Nom: {}", savedUser.getName());
            log.info("📧 Email: {}", savedUser.getEmail());
            log.info("========================================");

        } catch (Exception e) {
            log.error("❌ Erreur lors de la persistance du User: {}", e.getMessage(), e);
        }
    }
}
