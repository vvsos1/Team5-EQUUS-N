package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamFromSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
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
