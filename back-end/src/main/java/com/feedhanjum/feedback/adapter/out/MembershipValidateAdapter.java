package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.team.MembershipValidatePort;
import com.feedhanjum.team.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class MembershipValidateAdapter implements MembershipValidatePort {
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public boolean hasMembership(Long teamId, Long memberId) {
        return teamMemberRepository.findByMemberIdAndTeamId(memberId, teamId).isPresent();
    }
}
