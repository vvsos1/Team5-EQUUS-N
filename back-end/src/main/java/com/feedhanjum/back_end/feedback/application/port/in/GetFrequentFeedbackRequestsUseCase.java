package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.GetFrequentFeedbackRequestsCommand;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;

import java.util.List;

public interface GetFrequentFeedbackRequestsUseCase {

    List<FrequentFeedbackRequest> getFrequentFeedbackRequests(GetFrequentFeedbackRequestsCommand command);

}
