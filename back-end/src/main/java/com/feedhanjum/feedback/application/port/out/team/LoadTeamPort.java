package com.feedhanjum.feedback.application.port.out.team;

import com.feedhanjum.feedback.domain.AssociatedTeam;

import java.util.Optional;

public interface LoadTeamPort {
    Optional<AssociatedTeam> loadTeam(Long teamId);

}
