package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.Todo;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleResponseDto;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
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
class ScheduleServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMemberRepository scheduleMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성 성공")
    void createSchedule_성공() {
        //given
        Long memberId = 1L;
        Long teamId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto("haha", startTime, endTime, List.of(new Todo("hh")));

        Team team = mock(Team.class);
        Member member = mock(Member.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId)).thenReturn(Optional.of(new TeamMember(team, member)));
        when(scheduleRepository.findByStartTime(startTime)).thenReturn(Optional.empty());
        Schedule savedSchedule = new Schedule("haha", startTime, endTime, team, member);
        ScheduleDto scheduleDto = new ScheduleDto(savedSchedule);
        ScheduleMember scheduleMember = new ScheduleMember(savedSchedule, member);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);
        when(scheduleMemberRepository.save(any(ScheduleMember.class))).thenReturn(scheduleMember);

        //when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(memberId, teamId, requestDto);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getSchedule()).isEqualTo(scheduleDto);
        assertThat(responseDto.getTodoListDto()).hasSize(1);
        verify(scheduleMemberRepository).save(any(ScheduleMember.class));
    }

    @Test
    @DisplayName("팀이 없을 때 일정 생성 실패")
    void createSchedule_팀없음() {
        //given
        Long memberId = 1L;
        Long teamId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto("haha", startTime, endTime, List.of(new Todo("hoho")));

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> scheduleService.createSchedule(memberId, teamId, requestDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("사용자가 없을 때 일정 생성 실패")
    void createSchedule_사용자없음() {
        //given
        Long memberId = 1L;
        Long teamId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto("haha", startTime, endTime, List.of(new Todo("hoho")));

        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> scheduleService.createSchedule(memberId, teamId, requestDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("팀 멤버십이 없을 때 일정 생성 실패")
    void createSchedule_팀멤버십없음() {
        //given
        Long memberId = 1L;
        Long teamId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto("haha", startTime, endTime, List.of(new Todo("hoho")));

        Team team = mock(Team.class);
        Member member = mock(Member.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> scheduleService.createSchedule(memberId, teamId, requestDto))
                .isInstanceOf(TeamMembershipNotFoundException.class)
                .hasMessageContaining("해당 팀에 존재하는 사람만 일정을 생성할 수 있습니다.");
    }

    @Test
    @DisplayName("같은 시작시간 일정이 있을 때 일정 생성 실패")
    void createSchedule_중복일정() {
        //given
        Long memberId = 1L;
        Long teamId = 1L;
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        ScheduleRequestDto requestDto = new ScheduleRequestDto("haha", startTime, endTime, List.of(new Todo("hoho")));

        Team team = mock(Team.class);
        Member member = mock(Member.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId)).thenReturn(Optional.of(new TeamMember(team, member)));
        when(scheduleRepository.findByStartTime(startTime)).thenReturn(Optional.of(mock(Schedule.class)));

        //when, then
        assertThatThrownBy(() -> scheduleService.createSchedule(memberId, teamId, requestDto))
                .isInstanceOf(ScheduleAlreadyExistException.class)
                .hasMessageContaining("이미 같은 시작시간에 일정이 있습니다.");
    }
}
