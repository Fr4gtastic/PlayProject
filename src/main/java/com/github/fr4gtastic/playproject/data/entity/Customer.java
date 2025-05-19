package com.github.fr4gtastic.playproject.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Column(name = "id")
    private @Id
    @GeneratedValue Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "opted_in")
    private boolean optedIn;

    public Customer(String phoneNumber,
                    boolean optedIn) {
        this.phoneNumber = phoneNumber;
        this.optedIn = optedIn;
    }
}
