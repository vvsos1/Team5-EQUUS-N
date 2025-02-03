package com.feedhanjum.back_end.feedback.controller.dto.request;

import com.feedhanjum.back_end.core.constraints.ByteLength;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.ObjectiveFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegularFeedbackSendRequest(
        @NotNull
        Long receiverId,

        @NotNull
        Long scheduleId,

        @NotNull
        FeedbackFeeling feedbackFeeling,

        @Size(min = Feedback.MIN_OBJECTIVE_FEEDBACK_SIZE, max = Feedback.MAX_OBJECTIVE_FEEDBACK_SIZE)
        List<ObjectiveFeedback> objectiveFeedbacks,

        @NotBlank
        @ByteLength(min = Feedback.MIN_SUBJECTIVE_FEEDBACK_BYTE, max = Feedback.MAX_SUBJECTIVE_FEEDBACK_BYTE, strip = true)
        String subjectiveFeedback,

        @NotNull
        Boolean isAnonymous
) {
}
