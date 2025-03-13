package com.feedhanjum.team.event;

public record FrequentFeedbackRequestedEvent(Long senderId, Long teamId, Long receiverId) {
}
