package com.feedhanjum.feedback.application.port.out.team;

import com.feedhanjum.feedback.domain.AssociatedTeam;

import java.util.Optional;

public interface LoadTeamFromSchedulePort {
    Optional<AssociatedTeam> loadTeamFromSchedule(Long scheduleId);
}
