package com.feedhanjum.feedback.event;

import com.feedhanjum.feedback.domain.feedback.FeedbackId;

public record FeedbackLikedEvent(FeedbackId feedbackId) {
}
