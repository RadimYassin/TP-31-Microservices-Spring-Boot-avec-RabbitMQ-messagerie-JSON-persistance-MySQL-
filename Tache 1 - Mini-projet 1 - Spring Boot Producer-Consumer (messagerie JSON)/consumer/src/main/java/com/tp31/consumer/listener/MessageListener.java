package com.tp31.consumer.listener;

import com.tp31.consumer.config.RabbitMQConfig;
import com.tp31.consumer.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener pour consommer les messages de RabbitMQ
 * Utilise @RabbitListener pour écouter automatiquement la queue
 */
@Component
@Slf4j
public class MessageListener {

    /**
     * Méthode appelée automatiquement lors de la réception d'un message
     * Le message JSON est automatiquement désérialisé en MessageDto
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageDto message) {
        log.info("========================================");
        log.info("📨 MESSAGE REÇU !");
        log.info("========================================");
        log.info("📝 Contenu: {}", message.getContent());
        log.info("👤 Expéditeur: {}", message.getSender());
        log.info("🕐 Timestamp: {}", message.getTimestamp());
        log.info("========================================");

        // Ici, vous pouvez ajouter votre logique métier
        // Par exemple: traiter le message, appeler un service, etc.
    }
}
