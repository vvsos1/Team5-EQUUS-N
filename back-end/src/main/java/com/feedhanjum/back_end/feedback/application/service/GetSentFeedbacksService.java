package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetSentFeedbacksUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetSentFeedbacksCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadSentFeedbackPort;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetSentFeedbacksService implements GetSentFeedbacksUseCase {
    private final LoadSentFeedbackPort loadSentFeedbackPort;

    @Override
    public Page<SentFeedbackDto> getSentFeedbacks(GetSentFeedbacksCommand command) {
        return loadSentFeedbackPort.loadSentFeedback(command.getSenderId(), command.getTeamId(), command.isFilterHelpful(), command.getPage(), PAGE_SIZE, command.getDirection());
    }
}
