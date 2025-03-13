package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.GetReceivedFeedbacksCommand;
import com.feedhanjum.feedback.dto.ReceivedFeedbackDto;
import org.springframework.data.domain.Page;

public interface GetReceivedFeedbacksUseCase {
    int PAGE_SIZE = 10;

    Page<ReceivedFeedbackDto> getReceivedFeedbacks(GetReceivedFeedbacksCommand command);
}
