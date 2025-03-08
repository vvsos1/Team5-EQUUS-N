package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetSentFeedbacksCommand;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import org.springframework.data.domain.Page;

public interface GetSentFeedbacksUseCase {
    int PAGE_SIZE = 10;

    Page<SentFeedbackDto> getSentFeedbacks(GetSentFeedbacksCommand command);
}
