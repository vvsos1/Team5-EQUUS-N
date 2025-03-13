package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.domain.FeedbackReport;

public interface SaveFeedbackReportPort {
    void save(FeedbackReport feedbackReport);
}
