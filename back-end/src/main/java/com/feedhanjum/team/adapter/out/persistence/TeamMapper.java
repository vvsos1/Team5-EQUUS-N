package com.feedhanjum.team.adapter.out.persistence;

import com.feedhanjum.team.domain.Membership;
import com.feedhanjum.team.domain.Team;
import org.springframework.stereotype.Component;

@Component
class TeamMapper {

    public TeamJpaEntity fromDomain(Team team) {
        return new TeamJpaEntity(
                team.getId(),
                team.getName(),
                team.getStartDate(),
                team.getEndDate(),
                team.getFeedbackType(),
                team.getLeaderId(),
                team.getMemberships().stream().map(this::fromDomain).toList()
        );
    }

    private MembershipJpaEntity fromDomain(Membership membership) {
        return new MembershipJpaEntity();
    }
}
