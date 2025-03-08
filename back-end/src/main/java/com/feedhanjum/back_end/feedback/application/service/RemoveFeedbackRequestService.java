package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.request.RemoveFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.command.RemoveFeedbackRequestCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.DeleteFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class RemoveFeedbackRequestService implements RemoveFeedbackRequestUseCase {
    private final DeleteFrequentFeedbackRequestPort deleteFrequentFeedbackRequestPort;
    private final DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @Override
    @Transactional
    public void removeFeedbackRequest(RemoveFeedbackRequestCommand command) {
        Long teamId = command.getTeamId();
        Long memberId = command.getMemberId();
        deleteFrequentFeedbackRequestPort.deleteByTeamIdAndReceiverId(teamId, memberId);
        deleteFrequentFeedbackRequestPort.deleteByTeamIdAndRequesterId(teamId, memberId);
        deleteRegularFeedbackRequestPort.deleteByTeamIdAndReceiverId(teamId, memberId);
        deleteRegularFeedbackRequestPort.deleteByTeamIdAndRequesterId(teamId, memberId);
    }
}
