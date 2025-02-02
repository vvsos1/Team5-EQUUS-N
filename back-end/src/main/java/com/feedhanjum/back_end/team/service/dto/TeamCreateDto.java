package com.feedhanjum.back_end.team.service.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;

import java.time.LocalDateTime;

public record TeamCreateDto(
        String teamName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        FeedbackType feedbackType
) {
}
