package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LoadTeamAdapter implements LoadTeamPort {
    private final TeamRepository teamRepository;

    @Override
    public AssociatedTeam loadTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        return AssociatedTeam.of(team);
    }
}
