package com.feedhanjum.back_end.schedule.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.exception.ScheduleAlreadyExistException;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleResponseDto;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정을 생성하는 메소드
     *
     * @throws EntityNotFoundException 사용자 혹은 팀이 존재하지 않는 경우
     * @throws TeamMembershipNotFoundException 해당 사용자가 팀에 가입한 상태가 아닌경우
     * @throws ScheduleAlreadyExistException 해당 일정의 시작 시간에 일정이 존재하는 경우
     */
    @Transactional
    public ScheduleResponseDto createSchedule(Long memberId, Long teamId, ScheduleRequestDto scheduleDto){
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId).orElseThrow(() -> new TeamMembershipNotFoundException("해당 팀에 존재하는 사람만 일정을 생성할 수 있습니다."));

        if(scheduleRepository.findByStartTime(scheduleDto.startTime()).isPresent())
            throw new ScheduleAlreadyExistException("이미 같은 시작시간에 일정이 있습니다.");

        Schedule schedule = scheduleRepository.save(
                new Schedule(
                        scheduleDto.name(),
                        scheduleDto.startTime(),
                        scheduleDto.endTime(),
                        team,
                        member
                ));
        ScheduleMember scheduleMember = new ScheduleMember(schedule, member);
        scheduleMember.setTodos(scheduleDto.todos());
        return new ScheduleResponseDto(schedule, List.of(scheduleMember));
    }
}
