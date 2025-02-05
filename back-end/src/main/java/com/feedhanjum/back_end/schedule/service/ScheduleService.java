package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
import com.feedhanjum.back_end.schedule.exception.ScheduleIsAlreadyEndException;
import com.feedhanjum.back_end.schedule.exception.ScheduleMembershipNotFoundException;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleQueryRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final Clock clock;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    private final MemberRepository memberRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final MemberQueryRepository memberQueryRepository;

    /**
     * 일정을 생성하는 메소드
     *
     * @throws EntityNotFoundException         사용자 혹은 팀이 존재하지 않는 경우
     * @throws TeamMembershipNotFoundException 해당 사용자가 팀에 가입한 상태가 아닌경우
     * @throws ScheduleAlreadyExistException   해당 일정의 시작 시간에 일정이 존재하는 경우
     * @throws RuntimeException                내부 서버 오류: 방금 조회한 사용자 ID가 사라짐
     */
    @Transactional
    public void createSchedule(Long memberId, Long teamId, ScheduleRequestDto requestDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId).orElseThrow(() -> new TeamMembershipNotFoundException("해당 팀에 존재하는 사람만 일정을 생성할 수 있습니다."));

        validateScheduleDuplicate(teamId, requestDto);
        validateScheduleTimeIntoTeamTime(requestDto, team);
        validateEndTimeIsAfterNow(requestDto.endTime());

        Schedule schedule = scheduleRepository.save(
                new Schedule(
                        requestDto.name(),
                        requestDto.startTime(),
                        requestDto.endTime(),
                        team,
                        member));

        memberQueryRepository.findMembersByTeamId(teamId).forEach(m -> scheduleMemberRepository.save(new ScheduleMember(schedule, m)));
        ScheduleMember scheduleMember = scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, schedule.getId()).orElseThrow(() -> new RuntimeException("내부 서버 에러: 방금 조회한 사용자 ID가 사라짐"));
        scheduleMember.setTodos(requestDto.todos());
    }

    /**
     * @throws SecurityException                   일정 내용을 수정하려는 사람이 팀장, 일정의 주인이 아닌 경우
     * @throws EntityNotFoundException             팀, 사용자 또는 일정을 찾을 수 없는 경우
     * @throws ScheduleMembershipNotFoundException 회원이 해당 일정과 연관이 없을 경우
     * @throws IllegalArgumentException            일정의 시간 설정이 잘못된 경우
     */

    @Transactional
    public void updateSchedule(Long memberId, Long teamId, Long scheduleId, ScheduleRequestDto requestDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));

        validateIsEnded(schedule);
        changeScheduleInfo(requestDto, schedule, member, team);

        ScheduleMember scheduleMember = scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId)
                .orElseThrow(() -> new ScheduleMembershipNotFoundException("해당 사용자와 관련이 없는 일정입니다."));

        scheduleMember.setTodos(requestDto.todos());
    }

    private void validateIsEnded(Schedule schedule) {
        if (schedule.getEndTime().isBefore(LocalDateTime.now(clock))){
            throw new ScheduleIsAlreadyEndException("해당 일정은 이미 종료되었습니다.");
        }
    }

    private void changeScheduleInfo(ScheduleRequestDto requestDto, Schedule schedule, Member member, Team team) {
        changeName(schedule, requestDto, member, team);
        changeTime(schedule, requestDto, member, team);
    }

    private void changeTime(Schedule schedule, ScheduleRequestDto requestDto, Member member, Team team) {
        if (schedule.isTimeDifferent(requestDto.startTime(), requestDto.endTime())) {
            validateOwnerOrLeader(schedule, member, team);
            if (schedule.isStartTimeDifferent(requestDto.startTime())) {
                validateScheduleDuplicate(team.getId(), requestDto);
            }
            validateScheduleTimeIntoTeamTime(requestDto, team);
            validateEndTimeIsAfterNow(requestDto.endTime());
            schedule.setTime(requestDto.startTime(), requestDto.endTime());
        }
    }

    private void changeName(Schedule schedule, ScheduleRequestDto requestDto, Member member, Team team) {
        if (schedule.isNameDifferent(requestDto.name())) {
            validateOwnerOrLeader(schedule, member, team);
            schedule.changeName(requestDto.name());
        }
    }


    private void validateEndTimeIsAfterNow(LocalDateTime endTime) {
        if (LocalDateTime.now(clock).isAfter(endTime)){
            throw new IllegalArgumentException("일정의 종료 시점은 현재보다 미래여야 합니다.");
        }
    }

    private void validateScheduleTimeIntoTeamTime(ScheduleRequestDto requestDto, Team team) {
        if (team.getStartDate().isAfter(requestDto.startTime().toLocalDate())
                || team.getEndDate().isEqual(requestDto.endTime().toLocalDate())
        ) {
            throw new IllegalArgumentException("일정 시작 시간이 팀의 시작 시간 이후여야 합니다.");
        }
        if (team.getEndDate() != null
                && (team.getEndDate().isBefore(requestDto.endTime().toLocalDate())
                || team.getEndDate().isEqual(requestDto.endTime().toLocalDate()))) {
            throw new IllegalArgumentException("일정 종료 시간이 팀의 종료 시간 이전이어야 합니다.");
        }
    }

    private void validateOwnerOrLeader(Schedule schedule, Member member, Team team) {
        if (!(schedule.getOwner().equals(member) || team.getLeader().equals(member))) {
            throw new SecurityException("일정을 생성한 사람, 혹은 팀장만 일정을 수정할 수 있습니다.");
        }
    }

    private void validateScheduleDuplicate(Long teamId, ScheduleRequestDto requestDto) {
        if (scheduleRepository.findByTeamIdAndStartTime(teamId, requestDto.startTime()).isPresent())
            throw new ScheduleAlreadyExistException("이미 같은 시작시간에 일정이 있습니다.");
    }
}
