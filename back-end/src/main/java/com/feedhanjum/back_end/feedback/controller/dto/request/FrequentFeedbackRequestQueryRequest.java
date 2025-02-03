package com.feedhanjum.back_end.feedback.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record FrequentFeedbackRequestQueryRequest(
        @NotNull
        Long teamId
) {
}
