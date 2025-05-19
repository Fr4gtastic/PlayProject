package com.github.fr4gtastic.playproject.controller;

import com.github.fr4gtastic.playproject.config.Config;
import com.github.fr4gtastic.playproject.data.entity.Customer;
import com.github.fr4gtastic.playproject.data.entity.Sms;
import com.github.fr4gtastic.playproject.data.repository.CustomerRepository;
import com.github.fr4gtastic.playproject.data.repository.SmsRepository;
import com.github.fr4gtastic.playproject.logic.Evaluator;
import com.github.fr4gtastic.playproject.logic.OptInOutProcessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ApplicationController {

    private final Evaluator evaluator;
    private final CustomerRepository customerRepository;
    private final SmsRepository smsRepository;
    private final OptInOutProcessor optInOutProcessor;
    private final Config config;
    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @PostMapping("/message")
    public void message(@RequestBody Sms sms) {
        if (config.getNumberToOptInOut().equals(sms.getRecipient())) {
            optInOutProcessor.processRequest(sms);
        }
        var recipient = customerRepository.findByPhoneNumber(sms.getRecipient());
        if (recipient.isPresent()) {
            if (recipient.get().isOptedIn()) {
                var evaluationResult = evaluator.evaluate(sms);
                if (evaluationResult) {
                    smsRepository.save(sms);
                } else {
                    logger.info("Message from {} to {} was marked as suspicious and was rejected.", sms.getSender(), sms.getRecipient());
                }
            } else {
                logger.info("Skipping verification for number {}", sms.getRecipient());
            }
            smsRepository.save(sms);
        }
    }

    @GetMapping("/messages")
    public List<Sms> getMessages(@RequestParam String number) {
        return smsRepository.findAllByRecipient(number);
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
