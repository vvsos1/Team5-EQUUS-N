package com.feedhanjum.feedback.application.port.in.feedback.command;


import com.feedhanjum.core.SelfValidating;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.feedback.domain.feedback.ObjectiveFeedback;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SendFrequentFeedbackCommand extends SelfValidating<SendFrequentFeedbackCommand> {
    @NotNull
    private final Long senderId;
    @NotNull
    private final Long receiverId;
    @NotNull
    private final Long teamId;
    @NotNull
    private final FeedbackType feedbackType;
    @NotNull
    private final FeedbackFeeling feedbackFeeling;
    @Size(min = Feedback.MIN_OBJECTIVE_FEEDBACK_SIZE, max = Feedback.MAX_OBJECTIVE_FEEDBACK_SIZE)
    private final List<ObjectiveFeedback> objectiveFeedbacks;
    @Size(min = Feedback.MIN_SUBJECTIVE_FEEDBACK_BYTE, max = Feedback.MAX_SUBJECTIVE_FEEDBACK_BYTE)
    private final String subjectiveFeedback;

    public SendFrequentFeedbackCommand(Long senderId, Long receiverId, Long teamId, FeedbackType feedbackType, FeedbackFeeling feedbackFeeling, List<ObjectiveFeedback> objectiveFeedbacks, String subjectiveFeedback) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.teamId = teamId;
        this.feedbackType = feedbackType;
        this.feedbackFeeling = feedbackFeeling;
        this.objectiveFeedbacks = objectiveFeedbacks;
        this.subjectiveFeedback = subjectiveFeedback;
        validateSelf();
    }
}
