package com.github.fr4gtastic.playproject.service;

import com.github.fr4gtastic.playproject.dto.ThreatLevel;
import com.github.fr4gtastic.playproject.dto.EvaluationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static com.github.fr4gtastic.playproject.dto.ThreatType.MALWARE;
import static com.github.fr4gtastic.playproject.dto.ThreatType.SOCIAL_ENGINEERING;
import static com.github.fr4gtastic.playproject.dto.ThreatType.UNWANTED_SOFTWARE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class DummyEvaluationServiceTest {

    private Random random = Mockito.mock(Random.class);
    private EvaluationService evaluationService = new DummyEvaluationService(random);

    @Test
    void evaluateCorrectThreatTypesCountTest() {
        var request = new EvaluationRequest("www.test.pl", List.of(SOCIAL_ENGINEERING, MALWARE, UNWANTED_SOFTWARE));
        var response = evaluationService.evaluate(request);
        assertEquals(3, response.scores().size());
    }

    @Test
    void evaluateCorrectConfidenceLevelTest() {
        var request = new EvaluationRequest("www.test.pl", List.of(SOCIAL_ENGINEERING, MALWARE, UNWANTED_SOFTWARE));
        when(random.nextInt(anyInt())).thenReturn(0);
        var response = evaluationService.evaluate(request);
        assertEquals(ThreatLevel.SAFE, response.scores().get(0).threatLevel());
    }
}
