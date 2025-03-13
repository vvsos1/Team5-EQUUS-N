package com.feedhanjum.feedback.application.port.in.report;

import com.feedhanjum.feedback.application.port.in.report.command.GetFeedbackReportCommand;
import com.feedhanjum.feedback.domain.FeedbackReport;

import java.util.Optional;

public interface GetFeedbackReportUseCase {
    Optional<FeedbackReport> getFeedbackReport(GetFeedbackReportCommand command);
}
