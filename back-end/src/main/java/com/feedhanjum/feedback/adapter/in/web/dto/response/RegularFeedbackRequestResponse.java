package com.feedhanjum.feedback.adapter.in.web.dto.response;

import com.feedhanjum.feedback.domain.RegularFeedbackRequest;

import java.time.LocalDateTime;

public record RegularFeedbackRequestResponse(
        MemberResponse requester,
        Long scheduleId,
        LocalDateTime createdAt
) {
    public static RegularFeedbackRequestResponse from(RegularFeedbackRequest request) {
        return new RegularFeedbackRequestResponse(MemberResponse.from(request.getRequester()),
                request.getSchedule().getId(), request.getCreatedAt());
    }
}

