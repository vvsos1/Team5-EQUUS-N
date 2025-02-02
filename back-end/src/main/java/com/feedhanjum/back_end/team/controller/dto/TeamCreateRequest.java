package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TeamCreateRequest(
        @Size(min = 1, max = 20) String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        FeedbackType feedbackType
) {
}
