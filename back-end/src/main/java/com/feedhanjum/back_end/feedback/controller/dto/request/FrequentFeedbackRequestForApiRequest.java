package com.feedhanjum.back_end.feedback.controller.dto.request;

import com.feedhanjum.back_end.core.constraints.ByteLength;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import jakarta.validation.constraints.NotNull;

public record FrequentFeedbackRequestForApiRequest(
        @NotNull
        Long receiverId,

        @NotNull
        Long teamId,

        @ByteLength(min = FrequentFeedbackRequest.MIN_REQUESTED_CONTENT_BYTE, max = FrequentFeedbackRequest.MAX_REQUESTED_CONTENT_BYTE, strip = true)
        String requestedContent
) {
}
