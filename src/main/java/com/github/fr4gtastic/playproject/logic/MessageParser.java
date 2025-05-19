package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.dto.EvaluationOption;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class MessageParser {

    private static final String EVALUATION_OPTIONS_PATTERN =
            Arrays.stream(EvaluationOption.values())
                    .map(EvaluationOption::toString)
                    .collect(Collectors.joining("|"));
    // This pattern covers most normal cases, including optional protocol and "www"
    private static final String SIMPLE_URL_PATTERN =
            "^(https?://)?"
            + "(www\\.)?"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]+(\\.[a-zA-Z]{2,})+"
            + "(:\\d+)?(/\\S*)?$";

    public boolean isEvaluationOption(String message) {
        return message.matches(EVALUATION_OPTIONS_PATTERN);
    }

    public EvaluationOption toEvaluationOption(String message) {
        return EvaluationOption.valueOf(message);
    }

    public String getUriFromMessage(String message) {
        for (var word : message.split(" ")) {
            if (word.matches(SIMPLE_URL_PATTERN)) {
                return word;
            }
        }
        return "";
    }

}
