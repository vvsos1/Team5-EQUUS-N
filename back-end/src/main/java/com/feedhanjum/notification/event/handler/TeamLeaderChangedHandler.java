package com.feedhanjum.notification.event.handler;

import com.feedhanjum.notification.service.InAppNotificationService;
import com.feedhanjum.team.event.TeamLeaderChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class TeamLeaderChangedHandler {
    private final InAppNotificationService inAppNotificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(TeamLeaderChangedEvent event) {
        inAppNotificationService.createNotification(event);
    }
}
