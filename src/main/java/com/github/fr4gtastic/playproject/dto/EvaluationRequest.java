package com.github.fr4gtastic.playproject.dto;

import java.util.List;

public record EvaluationRequest(String uri, List<ThreatType> threatTypes) {
}
