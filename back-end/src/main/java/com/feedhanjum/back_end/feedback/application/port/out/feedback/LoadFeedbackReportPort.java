package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import com.feedhanjum.back_end.feedback.domain.FeedbackReport;

import java.util.Optional;

public interface LoadFeedbackReportPort {
    Optional<FeedbackReport> load(Long memberId);
}
