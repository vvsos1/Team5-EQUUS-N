package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.GetSentFeedbacksCommand;
import com.feedhanjum.back_end.feedback.service.dto.SentFeedbackDto;
import org.springframework.data.domain.Page;

public interface GetSentFeedbacksUseCase {

    Page<SentFeedbackDto> getSentFeedbacks(GetSentFeedbacksCommand command);
}
