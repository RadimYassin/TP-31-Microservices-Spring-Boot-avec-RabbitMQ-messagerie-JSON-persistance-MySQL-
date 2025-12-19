package com.tp31.producer.controller;

import com.tp31.producer.dto.UserDto;
import com.tp31.producer.service.UserProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Contrôleur REST pour publier des Users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProducer userProducer;

    /**
     * Endpoint pour publier un User vers RabbitMQ
     * POST http://localhost:8091/api/users/publish
     */
    @PostMapping("/publish")
    public ResponseEntity<String> publishUser(@RequestBody UserDto user) {
        // Ajouter le timestamp si non fourni
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        userProducer.publishUser(user);

        return ResponseEntity.ok(
                String.format("User '%s' publié avec succès vers RabbitMQ. " +
                        "Il sera persisté dans MySQL par le Consumer.",
                        user.getName()));
    }

    /**
     * Endpoint de test
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Producer User Service is running! 🚀");
    }
}
