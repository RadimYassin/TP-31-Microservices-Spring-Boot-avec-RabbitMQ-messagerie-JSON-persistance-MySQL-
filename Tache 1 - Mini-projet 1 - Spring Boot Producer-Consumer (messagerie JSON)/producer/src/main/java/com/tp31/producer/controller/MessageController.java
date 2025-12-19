package com.tp31.producer.controller;

import com.tp31.producer.dto.MessageDto;
import com.tp31.producer.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Contrôleur REST pour publier des messages
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducer messageProducer;

    /**
     * Endpoint pour publier un message vers RabbitMQ
     * POST http://localhost:8081/api/messages/send
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto message) {
        // Ajouter le timestamp si non fourni
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

        messageProducer.sendMessage(message);

        return ResponseEntity.ok(
                "Message publié avec succès: " + message.getContent());
    }

    /**
     * Endpoint de test pour vérifier que le service fonctionne
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Producer service is running! 🚀");
    }
}
