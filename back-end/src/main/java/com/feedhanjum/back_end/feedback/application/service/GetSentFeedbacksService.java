package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.GetSentFeedbacksUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetSentFeedbacksCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadSentFeedbackPort;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
class GetSentFeedbacksService implements GetSentFeedbacksUseCase {
    private final LoadSentFeedbackPort loadSentFeedbackPort;

    @Override
    @Transactional(readOnly = true)
    public Page<SentFeedbackDto> getSentFeedbacks(GetSentFeedbacksCommand command) {
        return loadSentFeedbackPort.loadSentFeedback(command.getSenderId(), command.getTeamId(), command.isFilterHelpful(), command.getPage(), PAGE_SIZE, command.getDirection());
    }
}
