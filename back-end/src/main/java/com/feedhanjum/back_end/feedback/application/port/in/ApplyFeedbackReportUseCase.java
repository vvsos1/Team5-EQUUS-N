package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.ApplyFeedbackReportCommand;

public interface ApplyFeedbackReportUseCase {
    void applyFeedbackReport(ApplyFeedbackReportCommand command);
}
