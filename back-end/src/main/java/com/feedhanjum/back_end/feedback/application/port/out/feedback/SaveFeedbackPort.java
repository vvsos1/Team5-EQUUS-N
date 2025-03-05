package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;

public interface SaveFeedbackPort {

    void saveFeedback(Feedback feedback);
}
