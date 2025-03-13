package com.feedhanjum.team.event;

public record TeamLeaderChangedEvent(Long teamId, Long newLeaderId) {
}
