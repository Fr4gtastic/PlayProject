package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.config.Config;
import com.github.fr4gtastic.playproject.data.entity.Sms;
import com.github.fr4gtastic.playproject.dto.ThreatLevel;
import com.github.fr4gtastic.playproject.dto.EvaluationResponse;
import com.github.fr4gtastic.playproject.dto.Score;
import com.github.fr4gtastic.playproject.dto.ThreatType;
import com.github.fr4gtastic.playproject.service.EvaluationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UriEvaluatorTest {

    @Mock
    Config config;
    @Mock
    EvaluationService evaluationService;
    @Mock
    MessageParser messageParser;

    @InjectMocks
    private UriEvaluator uriEvaluator;

    @Test
    void evaluateEmptyUriTest() {
        when(messageParser.getUriFromMessage(anyString())).thenReturn("");
        var sms = new Sms("634643634", "235253325", "");
        assertTrue(uriEvaluator.evaluate(sms));
    }

    @Test
    void evaluateSafeUriTest() {
        when(messageParser.getUriFromMessage(anyString())).thenReturn("www.test.pl");
        when(config.getThreatTypes()).thenReturn(ThreatType.values());
        when(config.getMaxSafeConfLevel()).thenReturn(ThreatLevel.MEDIUM);
        when(evaluationService.evaluate(any())).thenReturn(new EvaluationResponse(List.of(new Score(ThreatType.MALWARE, ThreatLevel.SAFE))));
        var sms = new Sms("634643634", "235253325", "www.test.pl");
        assertTrue(uriEvaluator.evaluate(sms));
    }

    @Test
    void evaluateUnsafeUriTest() {
        when(messageParser.getUriFromMessage(anyString())).thenReturn("www.test.pl");
        when(config.getThreatTypes()).thenReturn(ThreatType.values());
        when(config.getMaxSafeConfLevel()).thenReturn(ThreatLevel.MEDIUM);
        when(evaluationService.evaluate(any())).thenReturn(new EvaluationResponse(List.of(new Score(ThreatType.MALWARE, ThreatLevel.VERY_HIGH))));
        var sms = new Sms("634643634", "235253325", "www.test.pl");
        assertFalse(uriEvaluator.evaluate(sms));
    }
}
