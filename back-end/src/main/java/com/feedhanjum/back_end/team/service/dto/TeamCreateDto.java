package com.feedhanjum.back_end.team.service.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.team.controller.dto.TeamCreateRequest;

import java.time.LocalDateTime;

public record TeamCreateDto(
        String teamName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        FeedbackType feedbackType
) {
    public TeamCreateDto(TeamCreateRequest request) {
        this(request.name(), request.startTime(), request.endTime(), request.feedbackType());
    }
}
