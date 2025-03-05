package com.feedhanjum.back_end.feedback.adapter.in.web.dto.response;

import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.time.LocalDateTime;

public record RegularFeedbackRequestResponse(
        MemberResponse requester,
        Long scheduleId,
        LocalDateTime createdAt
) {
    public static RegularFeedbackRequestResponse from(RegularFeedbackRequest request) {
        FeedbackMember requester = request.getRequester();
        return new RegularFeedbackRequestResponse(new MemberResponse(requester.getId(), requester.getName(), requester.getProfileImage()),
                request.getSchedule().getId(), request.getCreatedAt());
    }
}

