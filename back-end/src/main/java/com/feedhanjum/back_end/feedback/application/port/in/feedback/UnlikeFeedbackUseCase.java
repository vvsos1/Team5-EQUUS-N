package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.UnlikeFeedbackCommand;

public interface UnlikeFeedbackUseCase {

    void unlikeFeedback(UnlikeFeedbackCommand command);
}
