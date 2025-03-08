package com.feedhanjum.back_end.feedback.domain.feedback;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class FeedbackId {
    private final Long id;

    public FeedbackId(Long id) {
        this.id = id;
    }
}
