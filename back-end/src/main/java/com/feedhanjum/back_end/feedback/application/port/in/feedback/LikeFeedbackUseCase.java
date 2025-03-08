package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.LikeFeedbackCommand;

public interface LikeFeedbackUseCase {

    void likeFeedback(LikeFeedbackCommand command);
}
