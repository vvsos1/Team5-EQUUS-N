package com.feedhanjum.back_end.feedback.service.dto;

import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.domain.ObjectiveFeedback;
import com.feedhanjum.back_end.member.domain.Member;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record ReceivedFeedback(
        Long feedbackId,
        boolean isAnonymous,
        @Nullable Sender sender,
        List<String> objectiveFeedbacks,
        String subjectiveFeedback,
        String teamName,
        LocalDateTime createdAt
) {

    public ReceivedFeedback(Long feedbackId, boolean isAnonymous, @Nullable Sender sender, List<String> objectiveFeedbacks, String subjectiveFeedback, String teamName, LocalDateTime createdAt) {
        this.feedbackId = feedbackId;
        this.isAnonymous = isAnonymous;
        this.objectiveFeedbacks = objectiveFeedbacks;
        this.subjectiveFeedback = subjectiveFeedback;
        this.teamName = teamName;
        this.createdAt = createdAt;
        if (isAnonymous)
            this.sender = null;
        else
            this.sender = sender;
    }

    public static ReceivedFeedback from(Feedback feedback) {
        return new ReceivedFeedback(
                feedback.getId(),
                feedback.getFeedbackType() == FeedbackType.ANONYMOUS,
                Sender.from(feedback.getSender()),
                feedback.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::getDescription).toList(),
                feedback.getSubjectiveFeedback(),
                feedback.getTeam().getName(),
                feedback.getCreatedAt()
        );
    }


    public record Sender(String name, String backgroundColor, String image) {

        public static Sender from(Member member) {
            return new Sender(member.getName(), member.getProfileImage().getBackgroundColor(), member.getProfileImage().getImage());
        }
    }

}
