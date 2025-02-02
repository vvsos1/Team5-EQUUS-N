package com.feedhanjum.back_end.team.service;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.exception.TeamLeaderMustExistException;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamQueryRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import com.feedhanjum.back_end.team.service.dto.TeamCreateDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private TeamQueryRepository teamQueryRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberQueryRepository memberQueryRepository;

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
        Member leader = mock(Member.class);

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

    @Test
    @DisplayName("내가 가입한 팀 목록 조회 성공")
    void getMyTeams_팀조회() {
        //given
        Long userId = 1L;
        Member leader = new Member("haha", "haha@hoho", new ProfileImage("blue", "image1"));
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamQueryRepository.findTeamByMemberId(userId)).thenReturn(List.of(team));

        //when
        List<Team> result = teamService.getMyTeams(userId);

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(team.getId());
    }

    @Test
    @DisplayName("팀원 제거 성공")
    void removeTeamMember_팀원제거_정상() {
        //given
        Long leaderId = 1L;
        Long teamId = 100L;
        Long memberIdToRemove = 3L;
        Member leader = mock(Member.class);
        Member member = new Member("huhu", "huhu@hehe", new ProfileImage("red", "image2"));
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(leader.getId()).thenReturn(leaderId);
        TeamMember membership = new TeamMember(team, member);
        when(teamMemberRepository.findByMemberIdAndTeamId(memberIdToRemove, teamId)).thenReturn(Optional.of(membership));

        //when
        teamService.removeTeamMember(leaderId, teamId, memberIdToRemove);

        //then
        verify(teamMemberRepository).delete(membership);
    }

    @Test
    @DisplayName("팀원 제거 실패 - 존재하지 않는 팀")
    void removeTeamMember_팀원제거_실패_팀X() {
        //given
        Long leaderId = 1L;
        Long teamId = 100L;
        Long memberIdToRemove = 1L;
        Member leader = mock(Member.class);
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> teamService.removeTeamMember(leaderId, teamId, memberIdToRemove))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("팀원 제거 실패 - 팀장 제거 시도")
    void removeTeamMember_팀원제거_실패_팀장제거() {
        //given
        Long leaderId = 1L;
        Long teamId = 100L;
        Long memberIdToRemove = 1L;
        Member leader = mock(Member.class);
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(leader.getId()).thenReturn(leaderId);

        //when & then
        assertThatThrownBy(() -> teamService.removeTeamMember(leaderId, teamId, memberIdToRemove))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("팀장은 제거할 수 없습니다.");
    }

    @Test
    @DisplayName("팀원 제거 실패 - 팀장이 아닌 경우")
    void removeTeamMember_팀원제거_실패_팀장아님() {
        //given
        Long leaderId = 1L;
        Long followerId = 2L;
        Long teamId = 100L;
        Long memberIdToRemove = 3L;
        Member leader = mock(Member.class);
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(team.getLeader().getId()).thenReturn(leaderId);

        //when & then
        assertThatThrownBy(() -> teamService.removeTeamMember(followerId, teamId, memberIdToRemove))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("현재 사용자는 팀장이 아닙니다.");
    }

    @Test
    @DisplayName("팀원 제거 실패 - 제거하려는 사용자가 팀원이 아닌 경우")
    void removeTeamMember_팀원제거_실패_팀원아님() {
        //given
        Long leaderId = 1L;
        Long teamId = 100L;
        Long memberIdToRemove = 3L;
        Member leader = mock(Member.class);
        Team team = new Team("haha", leader, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), FeedbackType.ANONYMOUS);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(team.getLeader().getId()).thenReturn(leaderId);
        when(teamMemberRepository.findByMemberIdAndTeamId(memberIdToRemove, teamId)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> teamService.removeTeamMember(leaderId, teamId, memberIdToRemove))
                .isInstanceOf(TeamMembershipNotFoundException.class)
                .hasMessageContaining("해당 팀원 정보를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("팀 리더 위임 정상 동작")
    void delegateTeamLeader_정상() {
        // given
        Long currentLeaderId = 1L;
        Long teamId = 1L;
        Long newLeaderId = 2L;
        Team team = mock(Team.class);
        Member currentLeader = mock(Member.class);
        when(currentLeader.getId()).thenReturn(currentLeaderId);
        when(team.getLeader()).thenReturn(currentLeader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        TeamMember teamMember = mock(TeamMember.class);
        Member newLeader = mock(Member.class);
        when(teamMember.getMember()).thenReturn(newLeader);
        when(teamMemberRepository.findByMemberIdAndTeamId(newLeaderId, teamId))
                .thenReturn(Optional.of(teamMember));

        // when
        teamService.delegateTeamLeader(currentLeaderId, teamId, newLeaderId);

        // then
        verify(team).changeLeader(newLeader);
    }

    @Test
    @DisplayName("팀을 찾을 수 없는 경우 예외 발생")
    void delegateTeamLeader_팀없음() {
        // given
        Long currentLeaderId = 1L;
        Long teamId = 1L;
        Long newLeaderId = 2L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> teamService.delegateTeamLeader(currentLeaderId, teamId, newLeaderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("현재 사용자가 팀장이 아닌 경우 예외 발생")
    void delegateTeamLeader_팀장아님() {
        // given
        Long currentLeaderId = 1L;
        Long teamId = 1L;
        Long newLeaderId = 2L;
        Team team = mock(Team.class);
        Member wrongLeader = mock(Member.class);
        when(wrongLeader.getId()).thenReturn(999L);
        when(team.getLeader()).thenReturn(wrongLeader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // when & then
        assertThatThrownBy(() -> teamService.delegateTeamLeader(currentLeaderId, teamId, newLeaderId))
                .isInstanceOf(SecurityException.class)
                .hasMessage("현재 사용자는 팀장이 아닙니다.");
    }

    @Test
    @DisplayName("새 팀장이 팀의 구성원이 아닌 경우 예외 발생")
    void delegateTeamLeader_팀구성원아님() {
        // given
        Long currentLeaderId = 1L;
        Long teamId = 1L;
        Long newLeaderId = 2L;
        Team team = mock(Team.class);
        Member currentLeader = mock(Member.class);
        when(currentLeader.getId()).thenReturn(currentLeaderId);
        when(team.getLeader()).thenReturn(currentLeader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        when(teamMemberRepository.findByMemberIdAndTeamId(newLeaderId, teamId))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> teamService.delegateTeamLeader(currentLeaderId, teamId, newLeaderId))
                .isInstanceOf(TeamMembershipNotFoundException.class)
                .hasMessage("새 팀장이 팀의 구성원이 아닙니다.");
    }

    @Test
    @DisplayName("일반 회원 탈퇴 - 정상 처리")
    void leaveTeam_일반회원탈퇴() {
        // given
        Long userId = 1L;
        Long teamId = 1L;
        Member leader = mock(Member.class);
        when(leader.getId()).thenReturn(2L);
        Team team = mock(Team.class);
        when(team.getLeader()).thenReturn(leader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberQueryRepository.countMembersByTeamId(teamId)).thenReturn(2L);
        TeamMember membership = mock(TeamMember.class);
        when(teamMemberRepository.findByMemberIdAndTeamId(userId, teamId)).thenReturn(Optional.of(membership));
        // when
        teamService.leaveTeam(userId, teamId);
        // then
        verify(teamMemberRepository).delete(membership);
    }

    @Test
    @DisplayName("나가려는 팀이 없을 시 예외 발생")
    void leaveTeam_팀정보없음() {
        // given
        Long userId = 1L;
        Long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> teamService.leaveTeam(userId, teamId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("나가려는 팀과의 관계가 없을 시 예외 발생")
    void leaveTeam_멤버십정보없음() {
        // given
        Long userId = 1L;
        Long teamId = 1L;
        Member leader = mock(Member.class);
        when(leader.getId()).thenReturn(2L);
        Team team = mock(Team.class);
        when(team.getLeader()).thenReturn(leader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberQueryRepository.countMembersByTeamId(teamId)).thenReturn(2L);
        when(teamMemberRepository.findByMemberIdAndTeamId(userId, teamId)).thenReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> teamService.leaveTeam(userId, teamId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("팀장 탈퇴 불가 예외 발생")
    void leaveTeam_팀장탈퇴불가() {
        // given
        Long userId = 1L;
        Long teamId = 1L;
        Member leader = mock(Member.class);
        when(leader.getId()).thenReturn(userId);
        Team team = mock(Team.class);
        when(team.getLeader()).thenReturn(leader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberQueryRepository.countMembersByTeamId(teamId)).thenReturn(2L);

        // when, then
        assertThatThrownBy(() -> teamService.leaveTeam(userId, teamId))
                .isInstanceOf(TeamLeaderMustExistException.class)
                .hasMessage("팀장은 반드시 팀에 존재해야 합니다. 팀장직을 다른사람에게 위임해 주세요.");
    }

    @Test
    @DisplayName("마지막 회원 탈퇴 - 성공")
    void leaveTeam_마지막멤버탈퇴() {
        // given
        Long userId = 1L;
        Long teamId = 1L;
        Member leader = mock(Member.class);
        when(leader.getId()).thenReturn(userId);
        Team team = mock(Team.class);
        when(team.getLeader()).thenReturn(leader);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberQueryRepository.countMembersByTeamId(teamId)).thenReturn(1L);
        TeamMember membership = mock(TeamMember.class);
        when(teamMemberRepository.findByMemberIdAndTeamId(userId, teamId)).thenReturn(Optional.of(membership));
        // when
        teamService.leaveTeam(userId, teamId);
        // then
        verify(teamMemberRepository).delete(membership);
    }
}