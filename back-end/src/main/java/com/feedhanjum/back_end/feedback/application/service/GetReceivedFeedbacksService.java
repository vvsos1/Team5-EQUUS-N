package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.GetReceivedFeedbacksUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetReceivedFeedbacksCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadReceivedFeedbackPort;
import com.feedhanjum.back_end.feedback.dto.ReceivedFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class GetReceivedFeedbacksService implements GetReceivedFeedbacksUseCase {
    private final LoadReceivedFeedbackPort loadReceivedFeedbackPort;

    @Override
    @Transactional(readOnly = true)
    public Page<ReceivedFeedbackDto> getReceivedFeedbacks(GetReceivedFeedbacksCommand command) {
        return loadReceivedFeedbackPort.loadReceivedFeedback(command.getReceiverId(),
                command.getTeamId(),
                command.isFilterHelpful(),
                command.getPage(),
                PAGE_SIZE,
                command.getDirection());
    }
}
