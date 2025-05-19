package com.github.fr4gtastic.playproject.data.repository;

import com.github.fr4gtastic.playproject.data.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsRepository extends JpaRepository<Sms, Long> {
    List<Sms> findAllByRecipient(String recipient);
}
