package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetReceivedFeedbackCountUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.GetSentFeedbackCountUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetReceivedFeedbackCountCommand;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetSentFeedbackCountCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.CountFeedbackPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFeedbackCountService implements GetSentFeedbackCountUseCase, GetReceivedFeedbackCountUseCase {
    private final CountFeedbackPort countFeedbackPort;

    @Override
    public Long getSentFeedbackCount(GetSentFeedbackCountCommand command) {
        return countFeedbackPort.countSentFeedback(command.getSenderId());
    }

    @Override
    public Long getReceivedFeedbackCount(GetReceivedFeedbackCountCommand command) {
        return countFeedbackPort.countReceivedFeedback(command.getReceiverId());
    }
}
