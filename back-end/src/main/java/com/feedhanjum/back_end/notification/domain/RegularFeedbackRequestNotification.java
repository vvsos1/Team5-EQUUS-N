package com.feedhanjum.back_end.notification.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.REGULAR_FEEDBACK_REQUEST)
public class RegularFeedbackRequestNotification extends InAppNotification {
    private String scheduleName;
    private Long scheduleId;

}
