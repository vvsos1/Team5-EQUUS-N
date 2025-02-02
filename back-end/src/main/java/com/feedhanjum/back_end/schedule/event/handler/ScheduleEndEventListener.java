package com.feedhanjum.back_end.schedule.event.handler;

import com.feedhanjum.back_end.feedback.service.FeedbackService;
import com.feedhanjum.back_end.schedule.event.ScheduleEndEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ScheduleEndEventListener {
    private final FeedbackService feedbackService;

    public ScheduleEndEventListener(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createRegularFeedbackRequests(ScheduleEndEvent event) {
        Long scheduleId = event.getScheduleId();
        feedbackService.createRegularFeedbackRequests(scheduleId);
    }
}
