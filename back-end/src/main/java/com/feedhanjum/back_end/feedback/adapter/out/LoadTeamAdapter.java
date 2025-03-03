package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamFromSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LoadTeamAdapter implements LoadTeamPort, LoadTeamFromSchedulePort {
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public AssociatedTeam loadTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        return AssociatedTeam.of(team);
    }

    @Override
    public AssociatedTeam loadTeamFromSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        Team team = schedule.getTeam();
        return AssociatedTeam.of(team);
    }
}
