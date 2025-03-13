package com.feedhanjum.feedback.test;

import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.feedback.domain.feedback.FeedbackIdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleFeedbackIdGenerator implements FeedbackIdGenerator {
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public FeedbackId generateFeedbackId() {
        return new FeedbackId(nextId.getAndIncrement());
    }
}
