package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.config.Config;
import com.github.fr4gtastic.playproject.data.entity.Sms;
import com.github.fr4gtastic.playproject.dto.EvaluationRequest;
import com.github.fr4gtastic.playproject.dto.EvaluationResponse;
import com.github.fr4gtastic.playproject.service.EvaluationService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@NoArgsConstructor(force = true)
public class UriEvaluator implements Evaluator {

    private final Config config;
    private final EvaluationService evaluationService;
    private final MessageParser messageParser;

    @Autowired
    public UriEvaluator(Config config, EvaluationService evaluationService, MessageParser messageParser) {
        this.config = config;
        this.evaluationService = evaluationService;
        this.messageParser = messageParser;
    }

    @Override
    public boolean evaluate(Sms sms) {
        var uri = messageParser.getUriFromMessage(sms.getMessage());

        if (!uri.isEmpty()) {
            var result = evaluationService.evaluate(new EvaluationRequest(uri, Arrays.asList(config.getThreatTypes())));
            return isResultAccepted(result);
        }
        return true;
    }

    private boolean isResultAccepted(EvaluationResponse response) {
        for (var score : response.scores()) {
            if (score.confidenceLevel().ordinal() > config.getMaxSafeConfLevel().ordinal()) {
                return false;
            }
        }
        return true;
    }
}
