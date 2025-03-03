package com.feedhanjum.back_end.feedback.event;

import com.feedhanjum.back_end.feedback.domain.FeedbackId;

public record FeedbackLikedEvent(FeedbackId feedbackId) {
}
