package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.RequestFrequentFeedbackCommand;

public interface RequestFrequentFeedbackUseCase {

    void requestFrequentFeedback(RequestFrequentFeedbackCommand command);

}
