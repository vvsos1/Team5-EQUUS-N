package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.domain.feedback.Feedback;

import java.util.List;

public interface SaveFeedbackPort {

    void saveFeedback(Feedback feedback);

    default void saveFeedbacks(List<Feedback> feedbacks) {
        feedbacks.forEach(this::saveFeedback);
    }
}
