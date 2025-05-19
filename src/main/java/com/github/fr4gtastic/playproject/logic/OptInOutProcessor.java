package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.data.entity.Customer;
import com.github.fr4gtastic.playproject.data.entity.Sms;
import com.github.fr4gtastic.playproject.data.repository.CustomerRepository;
import com.github.fr4gtastic.playproject.dto.EvaluationOption;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(force = true)
public class OptInOutProcessor {

    private final MessageParser messageParser;
    private final CustomerRepository customerRepository;
    private final Logger logger = LoggerFactory.getLogger(OptInOutProcessor.class);

    @Autowired
    public OptInOutProcessor(MessageParser messageParser, CustomerRepository customerRepository) {
        this.messageParser = messageParser;
        this.customerRepository = customerRepository;
    }

    public void processRequest(Sms sms) {
        var message = sms.getMessage();
        if (messageParser.isEvaluationOption(message)) {
            var option = messageParser.toEvaluationOption(message);
            optIn(sms.getSender(), EvaluationOption.START.equals(option));
        }
    }

    private void optIn(String customerNumber, boolean optedIn) {
        var optionalCustomer = customerRepository.findByPhoneNumber(customerNumber);
        if (optionalCustomer.isPresent()) {
            logger.info("Customer with number {} opted {} SMS evaluation.", customerNumber, optedIn ? "in for" : "out of");
            var customer = optionalCustomer.get();
            customer.setOptedIn(optedIn);
            customerRepository.save(customer);
        } else {
            logger.info("Customer with number {} not found, creating...", customerNumber);
            customerRepository.save(new Customer(customerNumber, optedIn));
        }
    }

}
