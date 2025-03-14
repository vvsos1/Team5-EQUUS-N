package com.feedhanjum.team.service;

import com.feedhanjum.core.event.EventPublisher;
import com.feedhanjum.member.domain.Member;
import com.feedhanjum.member.repository.MemberRepository;
import com.feedhanjum.schedule.repository.ScheduleQueryRepository;
import com.feedhanjum.team.domain.Team;
import com.feedhanjum.team.domain.TeamJoinToken;
import com.feedhanjum.team.event.TeamLeaderChangedEvent;
import com.feedhanjum.team.event.TeamMemberJoinEvent;
import com.feedhanjum.team.event.TeamMemberLeftEvent;
import com.feedhanjum.team.exception.TeamLeaderMustExistException;
import com.feedhanjum.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.team.repository.TeamJoinTokenRepository;
import com.feedhanjum.team.repository.TeamQueryRepository;
import com.feedhanjum.team.repository.TeamRepository;
import com.feedhanjum.team.service.dto.TeamCreateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamQueryRepository teamQueryRepository;
    private final EventPublisher eventPublisher;
    private final TeamJoinTokenRepository teamJoinTokenRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final Clock clock;

    /**
     * @throws IllegalArgumentException 프로젝트 기간의 시작일이 종료일보다 앞서지 않을 경우
     * @throws EntityNotFoundException  팀 생성 요청한 리더가 존재하지 않을 경우
     */
    @Transactional
    public Team createTeam(Long leaderId, TeamCreateDto teamCreateDto) {
        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Team team = new Team(teamCreateDto.teamName(), leader.getId(), teamCreateDto.startDate(), teamCreateDto.endDate(), teamCreateDto.feedbackType(), LocalDate.now(clock));
        teamRepository.save(team);
        return team;
    }

    @Transactional(readOnly = true)
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("해당 팀을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Team> getMyTeams(Long userId) {
        return teamQueryRepository.findTeamByMemberId(userId);
    }

    /**
     * 팀장이 팀원을 제거한다.
     *
     * @throws EntityNotFoundException      해당 팀 또는 팀원 정보가 없을 경우
     * @throws SecurityException            요청자가 팀장이 아닐 경우
     * @throws TeamLeaderMustExistException 팀장(자기 자신)을 제거하려 할 경우
     */
    @Transactional
    public void removeTeamMember(Long leaderId, Long teamId, Long memberIdToRemove) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));

        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new EntityNotFoundException("팀장을 찾을 수 없습니다"));
        Member removeTarget = memberRepository.findById(memberIdToRemove)
                .orElseThrow(() -> new EntityNotFoundException("팀원을 찾을 수 없습니다"));

        team.expel(leader.getId(), removeTarget.getId());

        eventPublisher.publishEvent(new TeamMemberLeftEvent(teamId, memberIdToRemove));
    }

    /**
     * @throws EntityNotFoundException         해당 팀 또는 회원이 존재하지 않을 경우
     * @throws SecurityException               현재 사용자가 팀장이 아닐 경우
     * @throws TeamMembershipNotFoundException 새 팀장이 팀의 구성원이 아닐 경우
     */
    @Transactional
    public void delegateTeamLeader(Long currentLeaderId, Long teamId, Long newLeaderId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member currentLeader = memberRepository.findById(currentLeaderId).orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));
        Member newLeader = memberRepository.findById(newLeaderId).orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));

        team.changeLeader(currentLeader.getId(), newLeader.getId());
        eventPublisher.publishEvent(new TeamLeaderChangedEvent(teamId, newLeaderId));
    }


    /**
     * @throws EntityNotFoundException      팀 가입 정보가 없을 경우
     * @throws TeamLeaderMustExistException 팀장은 탈퇴할 수 없으므로 발생
     */
    @Transactional
    public void leaveTeam(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));

        team.leave(member.getId());
        if (team.memberCount() == 0) {
            deleteTeam(team);
        }
        eventPublisher.publishEvent(new TeamMemberLeftEvent(teamId, memberId));
    }

    @Transactional
    public TeamJoinToken createJoinToken(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Member member = memberRepository
                .findById(memberId).orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));
        TeamJoinToken joinToken = team.createJoinToken(member.getId(), LocalDateTime.now(clock));
        teamJoinTokenRepository.save(joinToken);
        return joinToken;
    }

    @Transactional(readOnly = true)
    public Team getTeamByJoinToken(String token) {
        TeamJoinToken teamJoinToken = teamJoinTokenRepository.findById(token)
                .orElseThrow(() -> new EntityNotFoundException("토큰이 유효하지 않습니다."));
        return teamJoinToken.getTeamInfo();
    }

    @Transactional
    public Team joinTeam(Long memberId, String token) {
        TeamJoinToken teamJoinToken = teamJoinTokenRepository.findById(token)
                .orElseThrow(() -> new EntityNotFoundException("토큰이 유효하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));
        Team team = teamJoinToken.joinTeam(memberId);
        eventPublisher.publishEvent(new TeamMemberJoinEvent(memberId, teamJoinToken.getTeamInfo().getId()));
        return team;
    }


    private void deleteTeam(Team team) {
        teamRepository.delete(team);
    }
}
