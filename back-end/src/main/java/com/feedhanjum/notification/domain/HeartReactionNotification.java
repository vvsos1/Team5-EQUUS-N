package com.feedhanjum.notification.domain;

import com.feedhanjum.feedback.domain.feedback.Feedback;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.HEART_REACTION)
public class HeartReactionNotification extends InAppNotification {
    private String senderName;
    private String teamName;

    public HeartReactionNotification(Feedback feedback) {
        super(feedback.getSender().getId());
        this.senderName = feedback.getReceiver().getName();
        this.teamName = feedback.getTeam().getName();
    }
}
