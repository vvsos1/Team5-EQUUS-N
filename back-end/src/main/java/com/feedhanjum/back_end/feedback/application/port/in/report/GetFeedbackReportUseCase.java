package com.feedhanjum.back_end.feedback.application.port.in.report;

import com.feedhanjum.back_end.feedback.application.port.in.report.command.GetFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;

import java.util.Optional;

public interface GetFeedbackReportUseCase {
    Optional<FeedbackReport> getFeedbackReport(GetFeedbackReportCommand command);
}
