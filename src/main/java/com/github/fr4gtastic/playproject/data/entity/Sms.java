package com.github.fr4gtastic.playproject.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Sms {
    private @Id
    @GeneratedValue Long id;
    private String sender;
    private String recipient;
    private String message;

    public Sms(String sender,
               String recipient,
               String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }
}
