package com.feedhanjum.feedback.application.port.out.feedback;

public interface CountFeedbackPort {
    Long countReceivedFeedback(Long receiverId);

    Long countSentFeedback(Long senderId);
}
