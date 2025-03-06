package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import com.feedhanjum.back_end.feedback.domain.FeedbackReport;

public interface SaveFeedbackReportPort {
    void save(FeedbackReport feedbackReport);
}
