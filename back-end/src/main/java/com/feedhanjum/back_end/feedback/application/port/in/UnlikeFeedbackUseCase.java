package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.UnlikeFeedbackCommand;

public interface UnlikeFeedbackUseCase {

    void unlikeFeedback(UnlikeFeedbackCommand command);
}
