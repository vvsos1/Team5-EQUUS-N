package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.dto.SentFeedbackDto;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LoadSentFeedbackPort {

    Page<SentFeedbackDto> loadSentFeedback(long senderId, @Nullable Long teamId, boolean filterHelpful, int page, int pageSize, Sort.Direction sortOrder);
}
