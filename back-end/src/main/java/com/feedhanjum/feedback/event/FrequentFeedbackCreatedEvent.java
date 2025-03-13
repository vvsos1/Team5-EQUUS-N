package com.feedhanjum.feedback.event;

import com.feedhanjum.feedback.domain.feedback.FeedbackId;

public record FrequentFeedbackCreatedEvent(FeedbackId feedbackId, Long senderId, Long teamId, Long receiverId) {
}
