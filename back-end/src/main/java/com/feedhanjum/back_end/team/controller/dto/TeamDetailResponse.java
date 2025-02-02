package com.feedhanjum.back_end.team.controller.dto;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import lombok.Data;

import java.util.List;

@Data
public class TeamDetailResponse {
    private TeamResponse teamResponse;
    private List<MemberDto> members;
}
