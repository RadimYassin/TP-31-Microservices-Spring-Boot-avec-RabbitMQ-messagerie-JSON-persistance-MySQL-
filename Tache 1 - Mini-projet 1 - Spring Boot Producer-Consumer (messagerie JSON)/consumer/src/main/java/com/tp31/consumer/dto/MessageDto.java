package com.tp31.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO pour les messages JSON reçus via RabbitMQ
 * Doit correspondre au MessageDto du Producer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private String content;
    private String sender;
    private LocalDateTime timestamp;
}
