package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.Todo;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
import com.feedhanjum.back_end.schedule.exception.ScheduleMembershipNotFoundException;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MemberQueryRepository memberQueryRepository;

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

    @Nested
    @DisplayName("일정 생성 테스트")
    class CreateScheduleTest{

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
            when(memberQueryRepository.findMembersByTeamId(teamId)).thenReturn(List.of(member));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, savedSchedule.getId())).thenReturn(Optional.of(scheduleMember));

            //when
            scheduleService.createSchedule(memberId, teamId, requestDto);

            //then
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

    @Nested
    @DisplayName("일정 수정 테스트")
    class updateSchedule{

        @Test
        @DisplayName("일정 수정 성공")
        void updateSchedule_성공() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            Long scheduleId = 3L;
            LocalDateTime now = LocalDateTime.now();

            Member member = new Member("haha", "haha@example.com", new ProfileImage("bg", "img"));
            Team team = new Team("팀이름", member, now.minusDays(1), now.plusDays(1), FeedbackType.IDENTIFIED);
            LocalDateTime origStart = now.plusHours(1);
            LocalDateTime origEnd = now.plusHours(2);
            Schedule schedule = new Schedule("원래이름", origStart, origEnd, team, member);
            ScheduleMember scheduleMember = new ScheduleMember(schedule, member);

            LocalDateTime newStart = origStart.plusDays(1);
            LocalDateTime newEnd = origEnd.plusDays(1);
            List<Todo> todos = List.of(new Todo("hehe"));
            ScheduleRequestDto requestDto = new ScheduleRequestDto("huhu", newStart, newEnd, todos);

            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId))
                    .thenReturn(Optional.of(scheduleMember));
            when(scheduleRepository.findByStartTime(newStart)).thenReturn(Optional.empty());

            // when, then
            assertThatCode(() -> scheduleService.updateSchedule(memberId, teamId, scheduleId, requestDto))
                    .doesNotThrowAnyException();
            assertThat(schedule.getName()).isEqualTo("huhu");
            assertThat(schedule.getStartTime()).isEqualTo(newStart);
            assertThat(schedule.getEndTime()).isEqualTo(newEnd);
            assertThat(scheduleMember.getTodos()).extracting(Todo::getContent).containsExactly("hehe");
        }

        @Test
        @DisplayName("팀을 찾을 수 없는 경우")
        void updateSchedule_팀없음() {
            // given
            Long teamId = 2L;
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("huhu",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(1),
                    List.of(new Todo("hehe")));
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(1L, teamId, 3L, requestDto))
                    .withMessage("팀을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("사용자를 찾을 수 없는 경우")
        void updateSchedule_사용자없음() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            Team team = new Team("팀이름",
                    new Member("hoho", "hoho@example.com", new ProfileImage("bg", "img")),
                    LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
                    FeedbackType.IDENTIFIED);
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("huhu",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(1),
                    List.of(new Todo("hehe")));
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(memberId, teamId, 3L, requestDto))
                    .withMessage("해당 사용자를 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("일정을 찾을 수 없는 경우")
        void updateSchedule_일정없음() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            Member member = new Member("haha", "haha@example.com", new ProfileImage("bg", "img"));
            Team team = new Team("팀이름", member, LocalDateTime.now().minusDays(1),
                    LocalDateTime.now().plusDays(1), FeedbackType.IDENTIFIED);
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(scheduleRepository.findById(3L)).thenReturn(Optional.empty());

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("huhu",
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(1),
                    List.of(new Todo("hehe")));
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(memberId, teamId, 3L, requestDto))
                    .withMessage("해당 일정을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("일정 회원이 존재하지 않는 경우")
        void updateSchedule_일정회원없음() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            LocalDateTime now = LocalDateTime.now();
            Member member = new Member("haha", "haha@example.com", new ProfileImage("bg", "img"));
            Team team = new Team("팀이름", member, now.minusDays(1), now.plusDays(1), FeedbackType.IDENTIFIED);
            LocalDateTime origStart = now.plusHours(1);
            LocalDateTime origEnd = now.plusHours(2);
            Schedule schedule = new Schedule("원래이름", origStart, origEnd, team, member);
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(scheduleRepository.findById(3L)).thenReturn(Optional.of(schedule));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, 3L))
                    .thenReturn(Optional.empty());
            when(scheduleRepository.findByStartTime(origStart.plusDays(1))).thenReturn(Optional.empty());

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("huhu",
                    origStart.plusDays(1),
                    origEnd.plusDays(1),
                    List.of(new Todo("hehe")));
            assertThatExceptionOfType(ScheduleMembershipNotFoundException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(memberId, teamId, 3L, requestDto))
                    .withMessage("해당 사용자와 관련이 없는 일정입니다.");
        }

        @Test
        @DisplayName("수정 권한이 없는 경우")
        void updateSchedule_권한없음() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            LocalDateTime now = LocalDateTime.now();
            // 소유자와 요청 사용자가 다름
            Member owner = new Member("haha", "haha@example.com", new ProfileImage("bg", "img"));
            Member other = new Member("hoho", "hoho@example.com", new ProfileImage("bg", "img"));
            Team team = new Team("팀이름",
                    new Member("huhu", "huhu@example.com", new ProfileImage("bg", "img")),
                    now.minusDays(1), now.plusDays(1), FeedbackType.IDENTIFIED);
            LocalDateTime origStart = now.plusHours(1);
            LocalDateTime origEnd = now.plusHours(2);
            Schedule schedule = new Schedule("원래이름", origStart, origEnd, team, owner);
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(other));
            when(scheduleRepository.findById(3L)).thenReturn(Optional.of(schedule));

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("hehe",
                    origStart.plusDays(1),
                    origEnd.plusDays(1),
                    List.of(new Todo("huhu")));
            assertThatExceptionOfType(SecurityException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(memberId, teamId, 3L, requestDto))
                    .withMessage("일정을 생성한 사람, 혹은 팀장만 일정을 수정할 수 있습니다.");
        }

        @Test
        @DisplayName("시작 시간이 중복된 경우")
        void updateSchedule_중복일정() {
            // given
            Long memberId = 1L;
            Long teamId = 2L;
            LocalDateTime now = LocalDateTime.now();

            Member member = new Member("haha", "haha@example.com", new ProfileImage("bg", "img"));
            Team team = new Team("팀이름", member, now.minusDays(1), now.plusDays(1), FeedbackType.IDENTIFIED);
            LocalDateTime origStart = now.plusHours(1);
            LocalDateTime origEnd = now.plusHours(2);
            Schedule schedule = new Schedule("원래이름", origStart, origEnd, team, member);
            LocalDateTime newStart = origStart.plusDays(1);
            LocalDateTime newEnd = origEnd.plusDays(1);
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(scheduleRepository.findById(3L)).thenReturn(Optional.of(schedule));
            when(scheduleRepository.findByStartTime(newStart))
                    .thenReturn(Optional.of(new Schedule("dummy", newStart, newEnd, team, member)));

            // when, then
            ScheduleRequestDto requestDto = new ScheduleRequestDto("hehe", newStart, newEnd, List.of(new Todo("huhu")));
            assertThatExceptionOfType(ScheduleAlreadyExistException.class)
                    .isThrownBy(() -> scheduleService.updateSchedule(memberId, teamId, 3L, requestDto))
                    .withMessage("이미 같은 시작시간에 일정이 있습니다.");
        }
    }
}
