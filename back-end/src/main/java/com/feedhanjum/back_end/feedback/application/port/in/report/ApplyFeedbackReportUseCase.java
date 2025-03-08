package com.feedhanjum.back_end.feedback.application.port.in.report;

import com.feedhanjum.back_end.feedback.application.port.in.report.command.ApplyFeedbackReportCommand;

public interface ApplyFeedbackReportUseCase {
    void applyFeedbackReport(ApplyFeedbackReportCommand command);
}
