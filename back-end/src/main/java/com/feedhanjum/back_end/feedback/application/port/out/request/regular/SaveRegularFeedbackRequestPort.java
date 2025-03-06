package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

public interface SaveRegularFeedbackRequestPort {

    void save(RegularFeedbackRequest request);
}
