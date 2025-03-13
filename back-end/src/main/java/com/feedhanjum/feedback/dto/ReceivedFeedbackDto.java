package com.feedhanjum.feedback.dto;

import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.feedback.domain.feedback.ObjectiveFeedback;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record ReceivedFeedbackDto(
        Long feedbackId,
        boolean isAnonymous,
        @Nullable SenderDto sender,
        List<String> objectiveFeedbacks,
        String subjectiveFeedback,
        String teamName,
        boolean liked,
        LocalDateTime createdAt
) {

    public ReceivedFeedbackDto(Long feedbackId, boolean isAnonymous, @Nullable SenderDto sender, List<String> objectiveFeedbacks, String subjectiveFeedback, String teamName, boolean liked, LocalDateTime createdAt) {
        this.feedbackId = feedbackId;
        this.isAnonymous = isAnonymous;
        this.objectiveFeedbacks = objectiveFeedbacks;
        this.subjectiveFeedback = subjectiveFeedback;
        this.teamName = teamName;
        this.liked = liked;
        this.createdAt = createdAt;
        if (isAnonymous)
            this.sender = null;
        else
            this.sender = sender;
    }

    public ReceivedFeedbackDto(Feedback feedback) {
        this(feedback.getId().getId(),
                feedback.getFeedbackType() == FeedbackType.ANONYMOUS,
                SenderDto.from(feedback.getSender()),
                feedback.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::getDescription).toList(),
                feedback.getSubjectiveFeedback(),
                feedback.getTeam().getName(),
                feedback.isLiked(),
                feedback.getCreatedAt());
    }


    public record SenderDto(String name, String backgroundColor, String image) {
        public static SenderDto from(FeedbackMember member) {
            return new SenderDto(member.getName(), member.getProfileImage().getBackgroundColor(), member.getProfileImage().getImage());
        }
    }

}
