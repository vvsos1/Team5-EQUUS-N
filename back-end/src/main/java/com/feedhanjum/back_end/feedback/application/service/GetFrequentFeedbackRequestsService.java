package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetFrequentFeedbackRequestsUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetFrequentFeedbackRequestsCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.LoadFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
class GetFrequentFeedbackRequestsService implements GetFrequentFeedbackRequestsUseCase {
    private final LoadFrequentFeedbackRequestPort loadFrequentFeedbackRequestPort;

    @Override
    @Transactional(readOnly = true)
    public List<FrequentFeedbackRequest> getFrequentFeedbackRequests(GetFrequentFeedbackRequestsCommand command) {
        return loadFrequentFeedbackRequestPort.load(command.getTeamId(), command.getReceiverId());
    }
}
