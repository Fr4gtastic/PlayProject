package com.github.fr4gtastic.playproject.service;

import com.github.fr4gtastic.playproject.dto.ConfidenceLevel;
import com.github.fr4gtastic.playproject.dto.EvaluationRequest;
import com.github.fr4gtastic.playproject.dto.EvaluationResponse;
import com.github.fr4gtastic.playproject.dto.Score;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@NoArgsConstructor
public class DummyEvaluationService implements EvaluationService {

    private Random random = new Random();

    public DummyEvaluationService(Random random) {
        this.random = random;
    }

    @Override
    public EvaluationResponse evaluate(EvaluationRequest evaluationRequest) {
        List<Score> scores = new ArrayList<>();
        for (var threatType : evaluationRequest.threatTypes()) {
            scores.add(new Score(threatType, getDummyConfidenceLevel()));
        }
        return new EvaluationResponse(scores);
    }

    private ConfidenceLevel getDummyConfidenceLevel() {
        return ConfidenceLevel.values()[random.nextInt(ConfidenceLevel.values().length)];
    }
}
