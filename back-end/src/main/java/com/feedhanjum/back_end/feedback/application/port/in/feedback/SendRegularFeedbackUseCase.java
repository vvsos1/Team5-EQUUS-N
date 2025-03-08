package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.SendRegularFeedbackCommand;

public interface SendRegularFeedbackUseCase {
    void sendRegularFeedback(SendRegularFeedbackCommand command);
}
