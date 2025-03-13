package com.feedhanjum.team.repository;

import com.feedhanjum.team.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.feedhanjum.team.domain.QTeam.team;
import static com.feedhanjum.team.domain.QTeamMember.teamMember;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Team> findTeamByMemberId(Long memberId) {
        return jpaQueryFactory.select(team)
                .from(team)
                .join(teamMember).on(teamMember.team.id.eq(team.id)).fetchJoin()
                .where(teamMember.member.id.eq(memberId))
                .fetch();
    }
}
