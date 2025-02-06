package com.feedhanjum.back_end.team.event;

import lombok.Getter;

@Getter
public class TeamLeaderChangedEvent {
    private final Long teamId;
    private final Long newLeaderId;

    public TeamLeaderChangedEvent(Long teamId, Long newLeaderId) {
        this.teamId = teamId;
        this.newLeaderId = newLeaderId;
    }
}
