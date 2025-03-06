package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.GetSentFeedbacksCommand;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import org.springframework.data.domain.Page;

public interface GetSentFeedbacksUseCase {
    int PAGE_SIZE = 10;

    Page<SentFeedbackDto> getSentFeedbacks(GetSentFeedbacksCommand command);
}
