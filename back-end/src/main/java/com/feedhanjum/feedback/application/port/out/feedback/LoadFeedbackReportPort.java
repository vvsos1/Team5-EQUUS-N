package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.domain.FeedbackReport;

import java.util.Optional;

public interface LoadFeedbackReportPort {
    Optional<FeedbackReport> load(Long memberId);
}
