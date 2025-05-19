package com.github.fr4gtastic.playproject.logic;

import com.github.fr4gtastic.playproject.dto.EvaluationOption;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class MessageParser {

    private static final String EVALUATION_OPTIONS_PATTERN =
            Arrays.stream(EvaluationOption.values())
                    .map(EvaluationOption::toString)
                    .collect(Collectors.joining("|"));

    public boolean isEvaluationOption(String message) {
        return message.matches(EVALUATION_OPTIONS_PATTERN);
    }

    public EvaluationOption toEvaluationOption(String message) {
        return EvaluationOption.valueOf(message);
    }

    public String getUriFromMessage(String message) {
        var words = message.split(" ");

        for (var word : words) {
            try {
                var url = new URI(word);
                return url.toString();
            } catch (URISyntaxException ignored) {
            }
        }
        return "";
    }

}
