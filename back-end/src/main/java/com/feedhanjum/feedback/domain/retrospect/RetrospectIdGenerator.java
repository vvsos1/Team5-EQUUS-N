package com.feedhanjum.feedback.domain.retrospect;

import com.github.f4b6a3.tsid.TsidFactory;
import org.springframework.stereotype.Component;

@Component
public class RetrospectIdGenerator {
    private final TsidFactory factory = TsidFactory.newInstance256();

    public RetrospectId generateRetrospectId() {
        return new RetrospectId(factory.create().toLong());
    }
}
