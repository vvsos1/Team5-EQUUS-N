package com.feedhanjum.notification.event.handler;

import com.feedhanjum.feedback.event.FeedbackReportCreatedEvent;
import com.feedhanjum.notification.service.InAppNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class FeedbackReportCreatedHandler {
    private final InAppNotificationService inAppNotificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(FeedbackReportCreatedEvent event) {
        inAppNotificationService.createNotification(event);
    }
}
