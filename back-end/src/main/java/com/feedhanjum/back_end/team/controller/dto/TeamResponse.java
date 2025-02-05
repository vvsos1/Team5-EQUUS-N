package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.team.domain.Team;

import java.time.LocalDate;

public record TeamResponse(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        FeedbackType feedbackType,
        MemberDto leader
) {
    public TeamResponse(Team team) {
        this(
                team.getId(),
                team.getName(),
                team.getStartDate(),
                team.getEndDate(),
                team.getFeedbackType(),
                new MemberDto(team.getLeader()));
    }
}
