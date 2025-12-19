package com.tp31.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO pour les messages JSON échangés via RabbitMQ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private String content;
    private String sender;
    private LocalDateTime timestamp;

    public MessageDto(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }
}
