package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;

public interface LoadTeamPort {
    AssociatedTeam loadTeam(Long teamId);
}
