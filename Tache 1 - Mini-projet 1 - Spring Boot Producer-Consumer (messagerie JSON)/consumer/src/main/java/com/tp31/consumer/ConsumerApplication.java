package com.tp31.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale du Consumer
 * Mini-projet 1: Messagerie JSON avec RabbitMQ
 */
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        System.out.println("🚀 Consumer Service démarré sur le port 8082");
        System.out.println("👂 En écoute des messages RabbitMQ...");
    }
}
