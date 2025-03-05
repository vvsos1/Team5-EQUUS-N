package com.feedhanjum.back_end.feedback.infra;

import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.github.f4b6a3.tsid.TsidFactory;
import org.springframework.stereotype.Component;

@Component
public class FeedbackTSIDGenerator implements FeedbackIdGenerator {
    private final TsidFactory factory = TsidFactory.newInstance256();

    @Override
    public FeedbackId generateFeedbackId() {
        return new FeedbackId(factory.create().toLong());
    }
}
