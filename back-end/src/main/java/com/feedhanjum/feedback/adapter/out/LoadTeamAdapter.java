package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.team.LoadTeamFromSchedulePort;
import com.feedhanjum.feedback.application.port.out.team.LoadTeamPort;
import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.schedule.domain.Schedule;
import com.feedhanjum.schedule.repository.ScheduleRepository;
import com.feedhanjum.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
class LoadTeamAdapter implements LoadTeamPort, LoadTeamFromSchedulePort {
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Optional<AssociatedTeam> loadTeam(Long teamId) {
        return teamRepository
                .findById(teamId)
                .map(AssociatedTeam::of);
    }

    @Override
    public Optional<AssociatedTeam> loadTeamFromSchedule(Long scheduleId) {
        return scheduleRepository
                .findById(scheduleId)
                .map(Schedule::getTeam)
                .map(AssociatedTeam::of);
    }
}
