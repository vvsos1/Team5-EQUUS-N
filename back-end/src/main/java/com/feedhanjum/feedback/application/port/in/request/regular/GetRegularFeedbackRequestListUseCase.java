package com.feedhanjum.feedback.application.port.in.request.regular;

import com.feedhanjum.feedback.application.port.in.request.regular.command.GetRegularFeedbackRequestListCommand;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;

import java.util.List;

public interface GetRegularFeedbackRequestListUseCase {

    List<RegularFeedbackRequest> getRegularFeedbackRequestList(GetRegularFeedbackRequestListCommand command);
}
