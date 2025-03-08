package com.feedhanjum.back_end.feedback.application.port.in.request.regular;

import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.GetRegularFeedbackRequestListCommand;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.util.List;

public interface GetRegularFeedbackRequestListUseCase {

    List<RegularFeedbackRequest> getRegularFeedbackRequestList(GetRegularFeedbackRequestListCommand command);
}
