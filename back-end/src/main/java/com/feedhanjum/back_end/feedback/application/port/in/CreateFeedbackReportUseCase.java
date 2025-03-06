package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.CreateFeedbackReportCommand;

public interface CreateFeedbackReportUseCase {
    void createFeedbackReport(CreateFeedbackReportCommand command);
}
