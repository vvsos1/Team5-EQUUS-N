package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.team.domain.Team;

import java.time.LocalDateTime;

public record TeamResponse(
        Long id,
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        FeedbackType feedbackType,
        MemberDto leader
) {
    public TeamResponse(Team team) {
        this(
                team.getId(),
                team.getName(),
                team.getStartTime(),
                team.getEndTime(),
                team.getFeedbackType(),
                new MemberDto(team.getLeader()));
    }
}
