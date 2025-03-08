package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.RemoveRelatedFrequentFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RemoveRelatedFrequentFeedbackRequestCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.DeleteFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.LoadFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class RemoveRelatedFrequentFeedbackRequestService implements RemoveRelatedFrequentFeedbackRequestUseCase {
    private final DeleteFrequentFeedbackRequestPort deleteFrequentFeedbackRequestPort;
    private final LoadFrequentFeedbackRequestPort loadFrequentFeedbackRequestPort;

    @Override
    @Transactional
    public void deleteRelatedFrequentFeedback(RemoveRelatedFrequentFeedbackRequestCommand command) {
        Optional<FrequentFeedbackRequest> request = loadFrequentFeedbackRequestPort.load(command.getFeedbackReceiverId(), command.getTeamId(), command.getFeedbackSenderId());
        request.ifPresent(frequentFeedbackRequest -> deleteFrequentFeedbackRequestPort.deleteById(frequentFeedbackRequest.getId()));
    }
}
