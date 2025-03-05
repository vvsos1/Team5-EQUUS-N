package com.feedhanjum.back_end.feedback.application.port.in.command;


import com.feedhanjum.back_end.core.SelfValidating;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.back_end.feedback.domain.feedback.ObjectiveFeedback;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SendRegularFeedbackCommand extends SelfValidating<SendRegularFeedbackCommand> {
    @NotNull
    private final Long senderId;
    @NotNull
    private final Long receiverId;
    @NotNull
    private final Long scheduleId;
    @NotNull
    private final FeedbackType feedbackType;
    @NotNull
    private final FeedbackFeeling feedbackFeeling;
    @Size(min = Feedback.MIN_OBJECTIVE_FEEDBACK_SIZE, max = Feedback.MAX_OBJECTIVE_FEEDBACK_SIZE)
    private final List<ObjectiveFeedback> objectiveFeedbacks;
    @Size(min = Feedback.MIN_SUBJECTIVE_FEEDBACK_BYTE, max = Feedback.MAX_SUBJECTIVE_FEEDBACK_BYTE)
    private final String subjectiveFeedback;

    public SendRegularFeedbackCommand(Long senderId, Long receiverId, Long scheduleId, FeedbackType feedbackType, FeedbackFeeling feedbackFeeling, List<ObjectiveFeedback> objectiveFeedbacks, String subjectiveFeedback) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.scheduleId = scheduleId;
        this.feedbackType = feedbackType;
        this.feedbackFeeling = feedbackFeeling;
        this.objectiveFeedbacks = objectiveFeedbacks;
        this.subjectiveFeedback = subjectiveFeedback;
        validateSelf();
    }
}
