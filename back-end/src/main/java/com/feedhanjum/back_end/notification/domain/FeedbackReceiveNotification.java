package com.feedhanjum.back_end.notification.domain;

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


}
