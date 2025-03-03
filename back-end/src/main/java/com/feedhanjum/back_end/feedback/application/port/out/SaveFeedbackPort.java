package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.Feedback;

public interface SaveFeedbackPort {

    void saveFeedback(Feedback feedback);
}
