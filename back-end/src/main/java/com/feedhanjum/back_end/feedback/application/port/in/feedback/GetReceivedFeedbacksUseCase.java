package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetReceivedFeedbacksCommand;
import com.feedhanjum.back_end.feedback.dto.ReceivedFeedbackDto;
import org.springframework.data.domain.Page;

public interface GetReceivedFeedbacksUseCase {
    int PAGE_SIZE = 10;

    Page<ReceivedFeedbackDto> getReceivedFeedbacks(GetReceivedFeedbacksCommand command);
}
