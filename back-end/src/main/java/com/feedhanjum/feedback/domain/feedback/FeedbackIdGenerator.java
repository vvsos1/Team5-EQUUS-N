package com.feedhanjum.feedback.domain.feedback;

import com.github.f4b6a3.tsid.TsidFactory;
import org.springframework.stereotype.Component;

@Component
public class FeedbackIdGenerator {
    private final TsidFactory factory = TsidFactory.newInstance256();

    public FeedbackId generateFeedbackId() {
        return new FeedbackId(factory.create().toLong());
    }
}
