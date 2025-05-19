package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.data.entity.Sms;
import com.github.fr4gtastic.playproject.data.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OptInOptOutProcessorTest {

    @Mock
    private MessageParser messageParser;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OptInOutProcessor optInOutProcessor;

    @Test
    void processRequestNotValidOptionTest() {
        var sms = new Sms("1", "2", "test");
        verify(messageParser, never()).toEvaluationOption(anyString());
    }
}
