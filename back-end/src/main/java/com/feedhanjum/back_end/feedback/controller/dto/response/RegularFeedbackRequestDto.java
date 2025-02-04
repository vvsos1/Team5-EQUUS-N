package com.feedhanjum.back_end.feedback.controller.dto.response;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.schedule.domain.RegularFeedbackRequest;

import java.time.LocalDateTime;

public record RegularFeedbackRequestDto(
        MemberDto requester,
        Long scheduleId,
        LocalDateTime createdAt
) {
    public static RegularFeedbackRequestDto from(RegularFeedbackRequest request) {
        return new RegularFeedbackRequestDto(new MemberDto(request.getRequester()),
                request.getScheduleMember().getSchedule().getId(), request.getCreatedAt());
    }
}

