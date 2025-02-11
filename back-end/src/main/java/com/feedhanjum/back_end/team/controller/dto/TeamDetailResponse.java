package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamDetailResponse {
    @Schema(description = "팀 응답 정보")
    private TeamResponse teamResponse;

    @Schema(description = "일정의 가장 빠른 시작 시간")
    private LocalDateTime earliestScheduleStartTime;

    @Schema(description = "일정의 가장 늦은 종료 시간")
    private LocalDateTime latestScheduleEndTime;

    @Schema(description = "팀 멤버 목록")
    private List<MemberDto> members;
}
