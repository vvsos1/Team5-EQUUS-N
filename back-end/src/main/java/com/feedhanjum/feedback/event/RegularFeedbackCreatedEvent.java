package com.feedhanjum.feedback.event;

import com.feedhanjum.feedback.domain.feedback.FeedbackId;

public record RegularFeedbackCreatedEvent(FeedbackId feedbackId, Long senderId, Long receiverId) {
}
