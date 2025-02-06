package com.feedhanjum.back_end.notification.event;

import lombok.Getter;

@Getter
public class InAppNotificationCreatedEvent {
    private final Long notificationId;

    public InAppNotificationCreatedEvent(Long notificationId) {
        this.notificationId = notificationId;
    }
}
