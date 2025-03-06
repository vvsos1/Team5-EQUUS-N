package com.feedhanjum.back_end.feedback.dto;

import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.back_end.feedback.domain.feedback.ObjectiveFeedback;

import java.time.LocalDateTime;
import java.util.List;

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
    public SentFeedbackDto(Feedback feedback) {
        this(
                feedback.getId().getId(),
                feedback.getFeedbackType() == FeedbackType.ANONYMOUS,
                ReceiverDto.from(feedback.getReceiver()),
                feedback.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::getDescription).toList(),
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
