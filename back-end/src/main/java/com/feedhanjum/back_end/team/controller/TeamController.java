package com.feedhanjum.back_end.team.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.member.service.MemberService;
import com.feedhanjum.back_end.team.controller.dto.TeamCreateRequest;
import com.feedhanjum.back_end.team.controller.dto.TeamDetailResponse;
import com.feedhanjum.back_end.team.controller.dto.TeamResponse;
import com.feedhanjum.back_end.team.controller.dto.TeamUpdateRequest;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.service.TeamService;
import com.feedhanjum.back_end.team.service.dto.TeamCreateDto;
import com.feedhanjum.back_end.team.service.dto.TeamUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<TeamResponse> createTeam(@Login Long memberId,
                                                   @Valid @RequestBody TeamCreateRequest request) {
        Team team = teamService.createTeam(memberId,
                new TeamCreateDto(request));

        return new ResponseEntity<>(new TeamResponse(team), HttpStatus.CREATED);
    }

    @GetMapping("/my-teams")
    public ResponseEntity<List<TeamResponse>> getMyTeams(@Login Long memberId) {
        List<TeamResponse> teams = teamService.getMyTeams(memberId)
                .stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    /**
     * 팀 스페이스 관리 화면에서 쓰일 거 같아서 만들긴 했는데, 확실치 않아 주석 달아둡니다.
     *
     * @return 팀 정보 + 속한 회원 정보를 반환
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponse> getTeamDetail(@Login Long memberId, @PathVariable Long teamId) {
        Team team = teamService.getTeam(teamId);
        List<MemberDto> membersByTeam = memberService.getMembersByTeam(memberId, teamId)
                .stream().map(MemberDto::new).toList();
        TeamDetailResponse teamDetailResponse = new TeamDetailResponse();
        teamDetailResponse.setTeamResponse(new TeamResponse(team));
        teamDetailResponse.setMembers(membersByTeam);
        return ResponseEntity.ok(teamDetailResponse);
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<MemberDto>> getTeamMembers(@Login Long memberId, @PathVariable Long teamId) {
        List<MemberDto> membersByTeam = memberService.getMembersByTeam(memberId, teamId)
                .stream().map(MemberDto::new).toList();
        return ResponseEntity.ok(membersByTeam);
    }

    @DeleteMapping("/{teamId}/member/{removeMemberId}")
    public ResponseEntity<Void> deleteMemberFromTeam(@Login Long memberId, @PathVariable Long teamId, @PathVariable Long removeMemberId) {
        teamService.removeTeamMember(memberId, teamId, removeMemberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}")
    public ResponseEntity<TeamResponse> updateTeamInfo(@Login Long memberId, @PathVariable Long teamId, @Valid @RequestBody TeamUpdateRequest request){
        Team team = teamService.updateTeamInfo(memberId, teamId, new TeamUpdateDto(request));
        TeamResponse teamResponse = new TeamResponse(team);
        return ResponseEntity.ok(teamResponse);
    }

    @PostMapping("/{teamId}/leader")
    public ResponseEntity<Void> delegateTeamLeader(@Login Long memberId,
                                                   @PathVariable Long teamId,
                                                   @RequestParam Long newLeaderId) {
        teamService.delegateTeamLeader(memberId, teamId, newLeaderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teamId}/leave")
    public ResponseEntity<Void> leaveTeam(@Login Long memberId, @PathVariable Long teamId) {
        teamService.leaveTeam(memberId, teamId);
        return ResponseEntity.noContent().build();
    }
}
