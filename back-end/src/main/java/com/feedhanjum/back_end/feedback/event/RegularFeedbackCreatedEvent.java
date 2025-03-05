package com.feedhanjum.back_end.feedback.event;

import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;

public record RegularFeedbackCreatedEvent(FeedbackId feedbackId, Long senderId, Long receiverId) {
}
