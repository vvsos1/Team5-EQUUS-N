package com.feedhanjum.feedback.application.port.in.report;

import com.feedhanjum.feedback.application.port.in.report.command.CreateFeedbackReportCommand;

public interface CreateFeedbackReportUseCase {
    void createFeedbackReport(CreateFeedbackReportCommand command);
}
