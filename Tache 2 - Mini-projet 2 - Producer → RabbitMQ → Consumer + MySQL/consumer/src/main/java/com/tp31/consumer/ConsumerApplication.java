package com.tp31.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale du Consumer
 * Mini-projet 2: Messagerie User avec persistance MySQL
 */
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        System.out.println("🚀 Consumer User Service démarré sur le port 8092");
        System.out.println("👂 En écoute des messages RabbitMQ...");
        System.out.println("💾 Prêt à persister les Users dans MySQL");
    }
}
