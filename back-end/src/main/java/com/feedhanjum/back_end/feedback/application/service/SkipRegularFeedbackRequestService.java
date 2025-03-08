package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.request.regular.SkipRegularFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.SkipRegularFeedbackRequestCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
class SkipRegularFeedbackRequestService implements SkipRegularFeedbackRequestUseCase {
    private final LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;
    private final DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @Override
    @Transactional
    public void skipRegularFeedbackRequest(SkipRegularFeedbackRequestCommand command) {
        List<RegularFeedbackRequest> requests = loadRegularFeedbackRequestPort
                .load(command.getScheduleId(), command.getReceiverId());
        List<Long> requestIds = requests.stream().map(RegularFeedbackRequest::getId).toList();
        deleteRegularFeedbackRequestPort.deleteByIds(requestIds);
    }
}
