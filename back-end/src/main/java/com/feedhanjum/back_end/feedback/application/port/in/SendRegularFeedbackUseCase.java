package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.SendRegularFeedbackCommand;

public interface SendRegularFeedbackUseCase {
    void sendRegularFeedback(SendRegularFeedbackCommand command);
}
