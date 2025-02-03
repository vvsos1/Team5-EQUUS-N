package com.feedhanjum.back_end.team.repository;

import com.feedhanjum.back_end.team.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.feedhanjum.back_end.team.domain.QTeam.team;
import static com.feedhanjum.back_end.team.domain.QTeamMember.teamMember;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Team> findTeamByMemberId(Long memberId) {
        return jpaQueryFactory.select(team)
                .from(teamMember)
                .join(teamMember.team, team).fetchJoin()
                .where(teamMember.member.id.eq(memberId))
                .fetch();
    }
}
