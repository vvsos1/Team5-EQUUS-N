package com.feedhanjum.back_end.feedback.domain.feedback;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FeedbackId implements Serializable {
    private Long id;

    public FeedbackId(Long id) {
        this.id = id;
    }
}
