package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetRegularFeedbackRequestListUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetRegularFeedbackRequestListCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestListPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
class GetRegularFeedbackRequestListService implements GetRegularFeedbackRequestListUseCase {
    private final LoadRegularFeedbackRequestListPort loadRegularFeedbackRequestListPort;

    @Transactional(readOnly = true)
    @Override
    public List<RegularFeedbackRequest> getRegularFeedbackRequestList(GetRegularFeedbackRequestListCommand command) {
        return loadRegularFeedbackRequestListPort.loadRegularFeedbackRequestList(command.getScheduleId(), command.getReceiverId());
    }
}
