package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TeamDetailResponse {
    @Schema(description = "팀 응답 정보")
    private TeamResponse teamResponse;
    @Schema(description = "팀 멤버 목록")
    private List<MemberDto> members;
}
