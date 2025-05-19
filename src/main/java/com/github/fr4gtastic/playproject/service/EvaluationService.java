package com.github.fr4gtastic.playproject.service;

import com.github.fr4gtastic.playproject.dto.EvaluationRequest;
import com.github.fr4gtastic.playproject.dto.EvaluationResponse;

public interface EvaluationService {
    EvaluationResponse evaluate(EvaluationRequest evaluationRequest);
}
