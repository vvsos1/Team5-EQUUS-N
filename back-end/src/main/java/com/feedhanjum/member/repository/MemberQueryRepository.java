package com.feedhanjum.member.repository;

import com.feedhanjum.member.domain.Member;
import com.feedhanjum.team.domain.QMembership;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.feedhanjum.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMembership membership = QMembership.membership;

    public List<Member> findMembersByTeamId(Long teamId) {
        return jpaQueryFactory.select(member)
                .from(member)
                .join(membership).on(membership.member.id.eq(member.id)).fetchJoin()
                .where(membership.team.id.eq(teamId))
                .fetch();
    }

    public Long countMembersByTeamId(Long teamId) {
        return jpaQueryFactory.select(membership.count())
                .from(membership)
                .where(membership.team.id.eq(teamId))
                .fetchOne();
    }
}
