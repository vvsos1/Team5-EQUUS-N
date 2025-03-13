package com.feedhanjum.team.repository;

import com.feedhanjum.team.domain.QMembership;
import com.feedhanjum.team.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.feedhanjum.team.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMembership membership = QMembership.membership;

    public List<Team> findTeamByMemberId(Long memberId) {
        return jpaQueryFactory.select(team)
                .from(team)
                .join(membership).on(membership.team.id.eq(team.id)).fetchJoin()
                .where(membership.member.id.eq(memberId))
                .fetch();
    }
}
