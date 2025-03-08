package com.feedhanjum.back_end.feedback.application.port.out.request.frequent;

import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;

public interface SaveFrequentFeedbackRequestPort {
    void save(FrequentFeedbackRequest request);
}
