package com.feedhanjum.back_end.feedback.application.port.out.team;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;

import java.util.Optional;

public interface LoadTeamFromSchedulePort {
    Optional<AssociatedTeam> loadTeamFromSchedule(Long scheduleId);
}
