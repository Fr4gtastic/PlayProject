package com.github.fr4gtastic.playproject.config;

import com.github.fr4gtastic.playproject.dto.ConfidenceLevel;
import com.github.fr4gtastic.playproject.dto.ThreatType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "eval")
public class Config {
    private String numberToOptInOut;
    private ConfidenceLevel maxSafeConfLevel;
    private ThreatType[] threatTypes;
}
