package com.feedhanjum.back_end.team.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
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
    public List<Team> getMyTeams(Long userId) {
        return teamQueryRepository.findTeamByMemberId(userId);
    }
}
