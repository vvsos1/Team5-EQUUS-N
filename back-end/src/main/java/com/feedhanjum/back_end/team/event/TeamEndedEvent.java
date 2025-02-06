package com.feedhanjum.back_end.team.event;

import lombok.Getter;

@Getter
public class TeamEndedEvent {
    private final Long teamId;

    public TeamEndedEvent(Long teamId) {
        this.teamId = teamId;
    }
}
