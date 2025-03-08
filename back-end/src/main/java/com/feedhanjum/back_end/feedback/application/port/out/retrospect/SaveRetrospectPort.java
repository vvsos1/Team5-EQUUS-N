package com.feedhanjum.back_end.feedback.application.port.out.retrospect;

import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;

public interface SaveRetrospectPort {
    void save(Retrospect retrospect);
}
