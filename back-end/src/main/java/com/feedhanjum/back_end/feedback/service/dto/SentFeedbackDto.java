package com.feedhanjum.back_end.feedback.service.dto;

import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.domain.ObjectiveFeedback;
import com.feedhanjum.back_end.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

public record SentFeedbackDto(
        Long feedbackId,
        boolean isAnonymous,
        Receiver receiver,
        List<String> objectiveFeedbacks,
        String subjectiveFeedback,
        String teamName,
        boolean liked,
        LocalDateTime createdAt
) {


    public static SentFeedbackDto from(Feedback feedback) {
        return new SentFeedbackDto(
                feedback.getId(),
                feedback.getFeedbackType() == FeedbackType.ANONYMOUS,
                Receiver.from(feedback.getReceiver()),
                feedback.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::getDescription).toList(),
                feedback.getSubjectiveFeedback(),
                feedback.getTeam().getName(),
                feedback.isLiked(),
                feedback.getCreatedAt()
        );
    }


    public record Receiver(String name, String backgroundColor, String image) {
        public static Receiver from(Member member) {
            return new Receiver(member.getName(), member.getProfileImage().getBackgroundColor(), member.getProfileImage().getImage());
        }
    }

}
