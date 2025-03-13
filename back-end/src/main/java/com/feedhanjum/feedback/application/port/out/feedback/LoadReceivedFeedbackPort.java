package com.feedhanjum.feedback.application.port.out.feedback;

import com.feedhanjum.feedback.dto.ReceivedFeedbackDto;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LoadReceivedFeedbackPort {

    Page<ReceivedFeedbackDto> loadReceivedFeedback(long receiverId, @Nullable Long teamId, boolean filterHelpful, int page, int pageSize, Sort.Direction sortOrder);
}
