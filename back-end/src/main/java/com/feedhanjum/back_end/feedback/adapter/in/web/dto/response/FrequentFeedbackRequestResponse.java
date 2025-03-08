package com.feedhanjum.back_end.feedback.adapter.in.web.dto.response;

import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;

import java.time.LocalDateTime;

public record FrequentFeedbackRequestResponse(
        MemberResponse requester,
        Long teamId,
        String requestedContent,
        LocalDateTime createdAt
) {
    public static FrequentFeedbackRequestResponse from(FrequentFeedbackRequest request) {
        return new FrequentFeedbackRequestResponse(MemberResponse.from(request.getRequester()),
                request.getTeam().getId(), request.getRequestedContent(), request.getCreatedAt());
    }
}

