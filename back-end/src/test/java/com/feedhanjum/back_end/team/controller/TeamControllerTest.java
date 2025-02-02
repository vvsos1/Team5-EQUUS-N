package com.feedhanjum.back_end.team.controller;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.controller.dto.TeamCreateRequest;
import com.feedhanjum.back_end.team.controller.dto.TeamResponse;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.service.TeamService;
import com.feedhanjum.back_end.team.service.dto.TeamCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private TeamService teamService;
    @InjectMocks
    private TeamController teamController;


    @Test
    @DisplayName("팀 생성 컨트롤러 테스트")
    void createTeam_팀생성_컨트롤러() {
        //given
        Long memberId = 1L;
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = LocalDateTime.now().plusDays(10);
        TeamCreateRequest request = new TeamCreateRequest("haha", startTime,
                endTime, FeedbackType.ANONYMOUS);
        TeamCreateDto teamCreateDto = new TeamCreateDto(request);
        TeamResponse teamResponse = new TeamResponse(null, "haha", startTime,
                endTime, FeedbackType.ANONYMOUS, new MemberDto(new com.feedhanjum.back_end.member.domain.Member("haha", "haha@hoho", null)));
        when(teamService.createTeam(memberId, teamCreateDto))
                .thenReturn(new com.feedhanjum.back_end.team.domain.Team("haha", new com.feedhanjum.back_end.member.domain.Member("haha", "haha@hoho", null),
                        request.startTime(), request.endTime(), request.feedbackType()));

        //when
        ResponseEntity<TeamResponse> response = teamController.createTeam(memberId, request);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(teamResponse);
    }

    @Test
    @DisplayName("내 팀 조회 컨트롤러 테스트")
    void getMyTeams_팀조회_컨트롤러() {
        //given
        Long memberId = 1L;
        Team team = new Team("haha", new Member("haha", "haha@hoho", null), LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        TeamResponse teamResponse = new TeamResponse(team);
        when(teamService.getMyTeams(memberId)).thenReturn(List.of(team));

        //when
        ResponseEntity<List<TeamResponse>> response = teamController.getMyTeams(memberId);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0)).isEqualTo(teamResponse);
    }
}