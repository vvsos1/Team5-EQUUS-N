package com.feedhanjum.back_end.feedback.service.dto;

import com.feedhanjum.back_end.feedback.adapter.out.persistence.FeedbackJpaEntity;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record SentFeedbackDto(
        Long feedbackId,
        boolean isAnonymous,
        ReceiverDto receiver,
        List<String> objectiveFeedbacks,
        String subjectiveFeedback,
        String teamName,
        boolean liked,
        LocalDateTime createdAt
) {
    @QueryProjection
    public SentFeedbackDto(FeedbackJpaEntity feedback) {
        this(
                feedback.getId(),
                Objects.equals(feedback.getFeedbackType(), FeedbackType.ANONYMOUS.name()),
                ReceiverDto.from(feedback.getReceiver()),
                feedback.getObjectiveFeedbacks().stream().toList(),
                feedback.getSubjectiveFeedback(),
                feedback.getTeam().getName(),
                feedback.isLiked(),
                feedback.getCreatedAt()
        );
    }

    public record ReceiverDto(String name, String backgroundColor, String image) {
        public static ReceiverDto from(FeedbackMember receiver) {
            return new ReceiverDto(receiver.getName(), receiver.getProfileImage().getBackgroundColor(), receiver.getProfileImage().getImage());
        }
    }

}
