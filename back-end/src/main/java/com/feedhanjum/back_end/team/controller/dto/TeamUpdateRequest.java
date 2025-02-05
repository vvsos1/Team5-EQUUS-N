package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TeamUpdateRequest(
        @NotBlank @Size(min = 1, max = 20) String name,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        @NotNull FeedbackType feedbackType
) {
}

