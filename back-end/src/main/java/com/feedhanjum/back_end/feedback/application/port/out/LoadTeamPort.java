package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;

import java.util.Optional;

public interface LoadTeamPort {
    Optional<AssociatedTeam> loadTeam(Long teamId);

}
