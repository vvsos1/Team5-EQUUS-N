package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackId;

import java.util.Optional;

public interface LoadFeedbackPort {
    Optional<Feedback> loadFeedback(FeedbackId feedbackId);
}
