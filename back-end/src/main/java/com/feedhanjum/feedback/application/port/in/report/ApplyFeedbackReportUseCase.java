package com.feedhanjum.feedback.application.port.in.report;

import com.feedhanjum.feedback.application.port.in.report.command.ApplyFeedbackReportCommand;

public interface ApplyFeedbackReportUseCase {
    void applyFeedbackReport(ApplyFeedbackReportCommand command);
}
