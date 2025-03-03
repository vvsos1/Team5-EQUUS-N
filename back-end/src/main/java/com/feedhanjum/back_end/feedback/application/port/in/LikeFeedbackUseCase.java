package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.LikeFeedbackCommand;

public interface LikeFeedbackUseCase {

    void likeFeedback(LikeFeedbackCommand command);
}
