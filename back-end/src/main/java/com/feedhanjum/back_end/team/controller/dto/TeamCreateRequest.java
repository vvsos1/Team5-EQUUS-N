package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;

import java.time.LocalDateTime;

public record TeamCreateRequest(
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        FeedbackType feedbackType
) {
}
