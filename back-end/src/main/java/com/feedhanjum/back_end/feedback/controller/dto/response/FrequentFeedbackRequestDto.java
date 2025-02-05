package com.feedhanjum.back_end.feedback.controller.dto.response;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;

import java.time.LocalDateTime;

public record FrequentFeedbackRequestDto(
        MemberDto requester,
        Long teamId,
        String requestedContent,
        LocalDateTime createdAt
) {
    public static FrequentFeedbackRequestDto from(FrequentFeedbackRequest request) {
        return new FrequentFeedbackRequestDto(new MemberDto(request.getRequester()),
                request.getTeamMember().getTeam().getId(), request.getRequestedContent(), request.getCreatedAt());
    }
}

