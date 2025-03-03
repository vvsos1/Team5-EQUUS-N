package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import com.feedhanjum.back_end.feedback.domain.Feedback;

import java.util.List;

public interface LoadReceivedFeedbackPort {

    List<Feedback> loadReceivedFeedback(Long receiverId);
}
