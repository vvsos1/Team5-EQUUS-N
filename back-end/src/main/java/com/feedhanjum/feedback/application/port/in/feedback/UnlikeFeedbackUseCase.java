package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.UnlikeFeedbackCommand;

public interface UnlikeFeedbackUseCase {

    void unlikeFeedback(UnlikeFeedbackCommand command);
}
