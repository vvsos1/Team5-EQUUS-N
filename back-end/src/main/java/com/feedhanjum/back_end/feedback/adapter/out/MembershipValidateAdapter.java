package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.team.MembershipValidatePort;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
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
