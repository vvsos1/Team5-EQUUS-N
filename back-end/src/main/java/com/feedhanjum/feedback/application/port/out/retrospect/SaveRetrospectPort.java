package com.feedhanjum.feedback.application.port.out.retrospect;

import com.feedhanjum.feedback.domain.retrospect.Retrospect;

public interface SaveRetrospectPort {
    void save(Retrospect retrospect);
}
