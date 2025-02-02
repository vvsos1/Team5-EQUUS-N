package com.feedhanjum.back_end.team.service;

import com.feedhanjum.back_end.member.domain.Member;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final TeamQueryRepository teamQueryRepository;
    private final MemberQueryRepository memberQueryRepository;

    /**
     * @throws IllegalArgumentException 프로젝트 기간의 시작일이 종료일보다 앞서지 않을 경우
     * @throws EntityNotFoundException  팀 생성 요청한 리더가 존재하지 않을 경우
     */
    @Transactional
    public Team createTeam(Long leaderId, TeamCreateDto teamCreateDto) {
        if (!teamCreateDto.startTime().isBefore(teamCreateDto.endTime())) {
            throw new IllegalArgumentException("프로젝트 시작 시간이 종료 시간보다 앞서야 합니다.");
        }
        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Team team = new Team(teamCreateDto.teamName(), leader, teamCreateDto.startTime(), teamCreateDto.endTime(), teamCreateDto.feedbackType());
        teamRepository.save(team);

        TeamMember teamMember = new TeamMember(team, leader);
        teamMemberRepository.save(teamMember);
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
     * @throws EntityNotFoundException 해당 팀 또는 팀원 정보가 없을 경우
     * @throws SecurityException       요청자가 팀장이 아니거나 팀장을 제거하려 할 경우
     */
    @Transactional
    public void removeTeamMember(Long leaderId, Long teamId, Long memberIdToRemove) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new SecurityException("현재 사용자는 팀장이 아닙니다.");
        }
        if (team.getLeader().getId().equals(memberIdToRemove)) {
            throw new IllegalArgumentException("팀장은 제거할 수 없습니다.");
        }
        TeamMember membership = teamMemberRepository.findByMemberIdAndTeamId(memberIdToRemove, teamId)
                .orElseThrow(() -> new TeamMembershipNotFoundException("해당 팀원 정보를 찾을 수 없습니다."));
        teamMemberRepository.delete(membership);
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
        if (!team.getLeader().getId().equals(currentLeaderId)) {
            throw new SecurityException("현재 사용자는 팀장이 아닙니다.");
        }
        // 새 팀장이 팀 구성원인지 확인
        TeamMember newLeaderMembership = teamMemberRepository.findByMemberIdAndTeamId(newLeaderId, teamId)
                .orElseThrow(() -> new TeamMembershipNotFoundException("새 팀장이 팀의 구성원이 아닙니다."));
        Member newLeader = newLeaderMembership.getMember();
        team.changeLeader(newLeader);
    }

    /**
     * @throws EntityNotFoundException      팀 가입 정보가 없을 경우
     * @throws TeamLeaderMustExistException 팀장은 탈퇴할 수 없으므로 발생
     */
    @Transactional
    public void leaveTeam(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
        Long memberCount = memberQueryRepository.countMembersByTeamId(teamId);
        if (team.getLeader().getId().equals(userId) && memberCount >= 2) {
            throw new TeamLeaderMustExistException("팀장은 반드시 팀에 존재해야 합니다. 팀장직을 다른사람에게 위임해 주세요.");
        }
        TeamMember membership = teamMemberRepository.findByMemberIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀 멤버 정보를 찾을 수 없습니다."));
        teamMemberRepository.delete(membership);
        if (memberCount == 1) {
            deleteTeam(teamId);
        }
    }

    private void deleteTeam(Long teamId) {
        // 팀 삭제 로직 Todo
    }
}
