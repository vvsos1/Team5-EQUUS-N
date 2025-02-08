package com.feedhanjum.back_end.notification.domain;

import com.feedhanjum.back_end.feedback.domain.Feedback;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.FEEDBACK_RECEIVE)
public class FeedbackReceiveNotification extends InAppNotification {
    private String senderName;
    private String teamName;
    private Long teamId;

    public FeedbackReceiveNotification(Feedback receivedFeedback) {
        super(receivedFeedback.getReceiver().getId());
        this.senderName = receivedFeedback.getSender().getName();
        this.teamName = receivedFeedback.getTeam().getName();
        this.teamId = receivedFeedback.getTeam().getId();
    }
}
