package com.feedhanjum.notification.event.handler;

import com.feedhanjum.notification.event.InAppNotificationCreatedEvent;
import com.feedhanjum.notification.service.InAppNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class InAppNotificationCreatedHandler {
    private final InAppNotificationService inAppNotificationService;

    @Async
    @TransactionalEventListener
    public void on(InAppNotificationCreatedEvent event) {
        inAppNotificationService.sendPushNotification(event.notificationId());
    }
}
