package com.feedhanjum.back_end.feedback.application.port.in.report;

import com.feedhanjum.back_end.feedback.application.port.in.report.command.CreateFeedbackReportCommand;

public interface CreateFeedbackReportUseCase {
    void createFeedbackReport(CreateFeedbackReportCommand command);
}
