package com.feedhanjum.back_end.feedback.dto;

import com.feedhanjum.back_end.feedback.adapter.out.persistence.FeedbackJpaEntity;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @QueryProjection
    public ReceivedFeedbackDto(FeedbackJpaEntity feedback) {
        this(feedback.getId(),
                Objects.equals(feedback.getFeedbackType(), FeedbackType.ANONYMOUS.name()),
                SenderDto.from(feedback.getSender()),
                feedback.getObjectiveFeedbacks().stream().toList(),
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
