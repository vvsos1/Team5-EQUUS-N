package com.feedhanjum.back_end.feedback.event.handler;

import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.service.FeedbackCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FeedbackReceivedHandler {

    private final FeedbackCounterService feedbackCounterService;

    @Async
    @TransactionalEventListener
    public void on(FrequentFeedbackCreatedEvent event) {
        feedbackCounterService.incrementCounter(event.receiverId());
    }
}
