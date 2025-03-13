package com.feedhanjum.feedback.infra;

import com.feedhanjum.feedback.domain.retrospect.RetrospectId;
import com.feedhanjum.feedback.domain.retrospect.RetrospectIdGenerator;
import com.github.f4b6a3.tsid.TsidFactory;
import org.springframework.stereotype.Component;

@Component
class RetrospectTSIDGenerator implements RetrospectIdGenerator {
    private final TsidFactory factory = TsidFactory.newInstance256();

    @Override
    public RetrospectId generateRetrospectId() {
        return new RetrospectId(factory.create().toLong());
    }
}
