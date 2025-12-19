package com.tp31.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale du Producer
 * Mini-projet 2: Messagerie User avec persistance MySQL
 */
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        System.out.println("🚀 Producer User Service démarré sur le port 8091");
        System.out.println("📡 Prêt à publier des Users vers RabbitMQ");
    }
}
