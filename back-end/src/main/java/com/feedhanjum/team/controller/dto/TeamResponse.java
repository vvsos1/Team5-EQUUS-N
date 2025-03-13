package com.feedhanjum.team.controller.dto;

import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.member.controller.dto.MemberResponse;
import com.feedhanjum.team.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record TeamResponse(
        @Schema(description = "팀 고유 ID")
        Long id,

        @Schema(description = "팀 이름")
        String name,

        @Schema(description = "팀 시작 날짜")
        LocalDate startDate,

        @Schema(description = "팀 종료 날짜")
        LocalDate endDate,

        @Schema(description = "피드백 유형")
        FeedbackType feedbackType,

        @Schema(description = "팀장 정보")
        MemberResponse leader
) {
    public TeamResponse(Team team) {
        this(
                team.getId(),
                team.getName(),
                team.getStartDate(),
                team.getEndDate(),
                team.getFeedbackType(),
                new MemberResponse(team.getLeader()));
    }
}
