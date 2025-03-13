package com.feedhanjum.feedback.event.handler;

import com.feedhanjum.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.feedback.service.FeedbackRefineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@RequiredArgsConstructor
public class FeedbackSentHandler {

    private final FeedbackRefineService feedbackRefineService;

    @Async
    @TransactionalEventListener
    public void on(RegularFeedbackCreatedEvent event) {
        feedbackRefineService.resetRefineCount(event.senderId());
    }
}
