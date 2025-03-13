package com.feedhanjum.schedule.event;

public record RegularFeedbackRequestCreatedEvent(Long receiverId, Long scheduleId) {
}
