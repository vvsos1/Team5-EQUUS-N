package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.SendRegularFeedbackCommand;

public interface SendRegularFeedbackUseCase {
    void sendRegularFeedback(SendRegularFeedbackCommand command);
}
