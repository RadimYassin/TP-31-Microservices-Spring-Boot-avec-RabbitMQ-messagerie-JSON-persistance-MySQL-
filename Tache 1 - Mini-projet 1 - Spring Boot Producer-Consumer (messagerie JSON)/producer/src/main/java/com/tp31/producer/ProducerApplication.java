package com.tp31.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale du Producer
 * Mini-projet 1: Messagerie JSON avec RabbitMQ
 */
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        System.out.println("🚀 Producer Service démarré sur le port 8081");
        System.out.println("📡 Prêt à publier des messages vers RabbitMQ");
    }
}
