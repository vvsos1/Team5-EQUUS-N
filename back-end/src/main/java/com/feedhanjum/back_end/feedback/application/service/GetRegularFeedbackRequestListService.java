package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.request.regular.GetRegularFeedbackRequestListUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.GetRegularFeedbackRequestListCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
class GetRegularFeedbackRequestListService implements GetRegularFeedbackRequestListUseCase {
    private final LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;

    @Override
    @Transactional(readOnly = true)
    public List<RegularFeedbackRequest> getRegularFeedbackRequestList(GetRegularFeedbackRequestListCommand command) {
        return loadRegularFeedbackRequestPort.load(command.getScheduleId(), command.getReceiverId());
    }
}
