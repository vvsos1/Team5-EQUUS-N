package com.feedhanjum.team.service.dto;

import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.team.controller.dto.TeamUpdateRequest;

import java.time.LocalDate;

public record TeamUpdateDto(
        String teamName,
        LocalDate startDate,
        LocalDate endDate,
        FeedbackType feedbackType
) {
    public TeamUpdateDto(TeamUpdateRequest request) {
        this(request.name(), request.startDate(), request.endDate(), request.feedbackType());
    }
}
