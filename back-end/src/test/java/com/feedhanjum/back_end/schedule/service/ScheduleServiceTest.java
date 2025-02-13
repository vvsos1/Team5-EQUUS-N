package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.event.EventPublisher;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.Todo;
import com.feedhanjum.back_end.schedule.event.ScheduleCreatedEvent;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
import com.feedhanjum.back_end.schedule.exception.ScheduleIsAlreadyEndException;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleQueryRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
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

import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    Clock clock;

    @Mock
    TeamRepository teamRepository;

    @Mock
    TeamMemberRepository teamMemberRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleMemberRepository scheduleMemberRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ScheduleQueryRepository scheduleQueryRepository;

    @Mock
    EventPublisher eventPublisher;

    @Mock
    MemberQueryRepository memberQueryRepository;

    @InjectMocks
    ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성 성공")
    void createSchedule_성공() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        when(requestDto.name()).thenReturn("haha");
        LocalDateTime startTime = LocalDateTime.of(2025, 3, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 3, 1, 12, 0);
        when(requestDto.startTime()).thenReturn(startTime);
        when(requestDto.endTime()).thenReturn(endTime);
        Todo hehe = new Todo("hoho");
        when(requestDto.todos()).thenReturn(List.of(hehe));

        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(team.getStartDate()).thenReturn(LocalDate.of(2025, 2, 28));
        when(team.getEndDate()).thenReturn(LocalDate.of(2025, 3, 2));

        Member member = mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // 존재하는 팀 멤버 체크 (dummy object 사용)
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId))
                .thenReturn(Optional.of(new TeamMember(team, member)));

        // 중복 일정 없음
        when(scheduleRepository.findByTeamIdAndStartTime(teamId, startTime))
                .thenReturn(Optional.empty());

        // clock 고정
        when(clock.instant()).thenReturn(Instant.parse("2025-02-28T09:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        // 저장될 일정 mock
        Schedule schedule = mock(Schedule.class);
        when(schedule.getId()).thenReturn(100L);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        Member memberFromTeam = mock(Member.class);
        when(memberQueryRepository.findMembersByTeamId(teamId)).thenReturn(Collections.singletonList(memberFromTeam));

        ScheduleMember scheduleMember = mock(ScheduleMember.class);
        when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, 100L))
                .thenReturn(Optional.of(scheduleMember));

        // when
        scheduleService.createSchedule(memberId, teamId, requestDto);

        // then
        verify(scheduleMember, times(1)).setTodos(List.of(hehe));
        verify(eventPublisher).publishEvent(new ScheduleCreatedEvent(schedule.getId()));
    }

    @Test
    @DisplayName("일정 생성 실패 - 팀 미존재")
    void createSchedule_팀미존재() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() ->
                scheduleService.createSchedule(memberId, teamId, requestDto)
        ).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("팀을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("일정 생성 실패 - 사용자 미존재")
    void createSchedule_사용자미존재() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() ->
                scheduleService.createSchedule(memberId, teamId, requestDto)
        ).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("일정 생성 실패 - 팀 멤버 아님")
    void createSchedule_팀멤버아님() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        Member member = mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() ->
                scheduleService.createSchedule(memberId, teamId, requestDto)
        ).isInstanceOf(TeamMembershipNotFoundException.class)
                .hasMessageContaining("해당 팀에 존재하는 사람만 일정을 생성할 수 있습니다.");
    }

    @Test
    @DisplayName("일정 생성 실패 - 중복 일정 존재")
    void createSchedule_중복일정존재() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        when(requestDto.startTime()).thenReturn(LocalDateTime.of(2025, 3, 1, 10, 0));
        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        Member member = mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId))
                .thenReturn(Optional.of(new TeamMember(team, member)));

        when(scheduleRepository.findByTeamIdAndStartTime(teamId, LocalDateTime.of(2025, 3, 1, 10, 0)))
                .thenReturn(Optional.of(mock(Schedule.class)));

        // when, then
        assertThatThrownBy(() ->
                scheduleService.createSchedule(memberId, teamId, requestDto)
        ).isInstanceOf(ScheduleAlreadyExistException.class)
                .hasMessageContaining("이미 같은 시작시간에 일정이 있습니다.");
    }

    @Test
    @DisplayName("일정 수정 성공")
    void updateSchedule_성공() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        Long scheduleId = 100L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);
        when(requestDto.name()).thenReturn("haha");
        LocalDateTime newStartTime = LocalDateTime.of(2025, 4, 1, 10, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2025, 4, 1, 12, 0);
        when(requestDto.startTime()).thenReturn(newStartTime);
        when(requestDto.endTime()).thenReturn(newEndTime);
        Todo hehe = new Todo("hehe");
        when(requestDto.todos()).thenReturn(List.of(hehe));

        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        Member member = mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Schedule schedule = mock(Schedule.class);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        LocalDateTime futureTime = LocalDateTime.of(2025, 4, 1, 13, 0);
        when(schedule.getEndTime()).thenReturn(futureTime);

        ScheduleMember scheduleMember = mock(ScheduleMember.class);
        when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId))
                .thenReturn(Optional.of(scheduleMember));

        // clock 고정
        when(clock.instant()).thenReturn(Instant.parse("2025-04-01T09:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        // when
        scheduleService.updateSchedule(memberId, teamId, scheduleId, requestDto);

        // then
        verify(scheduleMember, times(1)).setTodos(List.of(hehe));
    }

    @Test
    @DisplayName("일정 수정 실패 - 이미 종료된 일정")
    void updateSchedule_종료된일정() {
        // given
        Long memberId = 1L;
        Long teamId = 1L;
        Long scheduleId = 100L;
        ScheduleRequestDto requestDto = mock(ScheduleRequestDto.class);

        Team team = mock(Team.class);
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        Member member = mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Schedule schedule = mock(Schedule.class);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        // 종료 시간이 현재 시간보다 이전
        LocalDateTime pastTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        when(schedule.getEndTime()).thenReturn(pastTime);

        when(clock.instant()).thenReturn(Instant.parse("2025-02-01T09:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        // when, then
        assertThatThrownBy(() ->
                scheduleService.updateSchedule(memberId, teamId, scheduleId, requestDto)
        ).isInstanceOf(ScheduleIsAlreadyEndException.class)
                .hasMessageContaining("해당 일정은 이미 종료되었습니다.");
    }
}
