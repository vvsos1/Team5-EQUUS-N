package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;

import java.util.List;
import java.util.Optional;

public interface LoadFeedbackPort {
    Optional<Feedback> loadFeedback(FeedbackId feedbackId);

    List<Feedback> loadFeedbacks();
}
