package com.feedhanjum.feedback.application.port.out.request.frequent;

import com.feedhanjum.feedback.domain.FrequentFeedbackRequest;

public interface SaveFrequentFeedbackRequestPort {
    void save(FrequentFeedbackRequest request);
}
