package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TeamUpdateRequest(
        @NotNull Long id,
        @NotBlank @Size(min = 1, max = 20) String name,
        @NotNull LocalDateTime startTime,
        LocalDateTime endTime,
        @NotNull FeedbackType feedbackType
) {
}

