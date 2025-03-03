package com.feedhanjum.back_end.feedback.test;

import com.feedhanjum.back_end.feedback.domain.FeedbackId;
import com.feedhanjum.back_end.feedback.domain.FeedbackIdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleFeedbackIdGenerator implements FeedbackIdGenerator {
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public FeedbackId generateFeedbackId() {
        return new FeedbackId(nextId.getAndIncrement());
    }
}
