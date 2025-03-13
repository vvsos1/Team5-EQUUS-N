package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.LikeFeedbackCommand;

public interface LikeFeedbackUseCase {

    void likeFeedback(LikeFeedbackCommand command);
}
