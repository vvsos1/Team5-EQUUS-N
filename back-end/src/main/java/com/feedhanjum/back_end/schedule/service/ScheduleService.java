package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
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

@Service
@RequiredArgsConstructor
public class ScheduleService {

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
     * @throws EntityNotFoundException 사용자 혹은 팀이 존재하지 않는 경우
     * @throws TeamMembershipNotFoundException 해당 사용자가 팀에 가입한 상태가 아닌경우
     * @throws ScheduleAlreadyExistException 해당 일정의 시작 시간에 일정이 존재하는 경우
     * @throws RuntimeException 내부 서버 오류: 방금 조회한 사용자 ID가 사라짐
     */
    @Transactional
    public void createSchedule(Long memberId, Long teamId, ScheduleRequestDto requestDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId).orElseThrow(() -> new TeamMembershipNotFoundException("해당 팀에 존재하는 사람만 일정을 생성할 수 있습니다."));

        validateScheduleDuplicate(requestDto);

        Schedule schedule = scheduleRepository.save(
                new Schedule(
                        requestDto.name(),
                        requestDto.startTime(),
                        requestDto.endTime(),
                        team,
                        member
                ));
        memberQueryRepository.findMembersByTeamId(teamId).forEach(m -> scheduleMemberRepository.save(new ScheduleMember(schedule, m)));
        ScheduleMember scheduleMember = scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, schedule.getId()).orElseThrow(() -> new RuntimeException("내부 서버 에러: 방금 조회한 사용자 ID가 사라짐"));
        scheduleMember.setTodos(requestDto.todos());
    }

    /**
     * @throws EntityNotFoundException 팀, 사용자 또는 일정을 찾을 수 없는 경우
     * @throws TeamMembershipNotFoundException 회원이 팀에 존재하지 않을 경우
     */
    @Transactional
    public void updateSchedule(Long memberId, Long teamId, Long scheduleId, ScheduleRequestDto requestDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));

        changeScheduleInfo(requestDto, schedule, member, team);

        ScheduleMember scheduleMember = scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId)
                .orElseThrow(() -> new TeamMembershipNotFoundException("회원이 팀에 존재하지 않습니다."));

        scheduleMember.setTodos(requestDto.todos());
    }

    // 일정 및 전체 할 일 조회 Todo
    // 가장 가까운 일정 조회 Todo

    private void changeScheduleInfo(ScheduleRequestDto requestDto, Schedule schedule, Member member, Team team) {
        if (schedule.isDifferent(requestDto.name(), requestDto.startTime(), requestDto.endTime())) {
            validateOwnerOrLeader(requestDto, schedule, member, team);
            if(!schedule.getName().equals(requestDto.name())){
                schedule.changeName(requestDto.name());
            }
            if(!schedule.getStartTime().isEqual(requestDto.startTime())){
                validateScheduleDuplicate(requestDto);
                schedule.changeStartTime(requestDto.startTime());
            }
            if(!schedule.getEndTime().isEqual(requestDto.endTime())){
                schedule.changeEndTime(requestDto.endTime());
            }
        }
    }

    private void validateOwnerOrLeader(ScheduleRequestDto requestDto, Schedule schedule, Member member, Team team) {
        if (!(schedule.getOwner().equals(member) || team.getLeader().equals(member))) {
            throw new SecurityException("일정을 생성한 사람, 혹은 팀장만 일정을 수정할 수 있습니다.");
        }
    }

    private void validateScheduleDuplicate(ScheduleRequestDto requestDto) {
        if (scheduleRepository.findByStartTime(requestDto.startTime()).isPresent())
            throw new ScheduleAlreadyExistException("이미 같은 시작시간에 일정이 있습니다.");
    }
}
