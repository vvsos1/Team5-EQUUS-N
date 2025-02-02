package com.feedhanjum.back_end.team.service;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import com.feedhanjum.back_end.team.service.dto.TeamCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("프로젝트 기간이 올바른 경우 팀 생성 성공")
    void createTeam_팀생성() {
        //given
        Long userId = 1L;
        String teamName = "haha";
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = LocalDateTime.now().plusDays(10);
        FeedbackType feedbackType = FeedbackType.ANONYMOUS;
        Member leader = new Member("haha", "haha@hoho", new ProfileImage("blue", "image1"));

        ReflectionTestUtils.setField(leader, "id", userId);

        when(memberRepository.findById(userId)).thenReturn(Optional.of(leader));
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(teamMemberRepository.save(any(TeamMember.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Team team = teamService.createTeam(userId, new TeamCreateDto(teamName, startTime, endTime, feedbackType));

        //then
        assertThat(team.getName()).isEqualTo(teamName);
        assertThat(team.getLeader()).isEqualTo(leader);
        assertThat(team.getStartTime()).isEqualTo(startTime);
        assertThat(team.getEndTime()).isEqualTo(endTime);
        assertThat(team.getFeedbackType()).isEqualTo(feedbackType);
    }

    @Test
    @DisplayName("프로젝트 기간이 올바르지 않을 경우 팀 생성 실패")
    void createTeam_프로젝트기간오류() {
        //given
        Long userId = 1L;
        String teamName = "haha";
        LocalDateTime startTime = LocalDateTime.now().plusDays(10);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1);
        FeedbackType feedbackType = FeedbackType.ANONYMOUS;

        //when & then
        assertThatThrownBy(() -> teamService.createTeam(userId, new TeamCreateDto(teamName, startTime, endTime, feedbackType)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("프로젝트 시작 시간이 종료 시간보다 앞서야 합니다.");
    }

}