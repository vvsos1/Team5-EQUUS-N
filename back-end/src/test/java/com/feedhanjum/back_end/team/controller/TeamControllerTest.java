package com.feedhanjum.back_end.team.controller;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.team.controller.dto.TeamCreateRequest;
import com.feedhanjum.back_end.team.controller.dto.TeamResponse;
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

}