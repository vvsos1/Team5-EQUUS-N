package com.feedhanjum.team.service.dto;

import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.team.controller.dto.TeamCreateRequest;

import java.time.LocalDate;

public record TeamCreateDto(
        String teamName,
        LocalDate startDate,
        LocalDate endDate,
        FeedbackType feedbackType
) {
    public TeamCreateDto(TeamCreateRequest request) {
        this(request.name(), request.startDate(), request.endDate(), request.feedbackType());
    }
}
