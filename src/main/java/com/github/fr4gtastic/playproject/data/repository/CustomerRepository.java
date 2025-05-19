package com.github.fr4gtastic.playproject.data.repository;

import com.github.fr4gtastic.playproject.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
