package com.feedhanjum.feedback.domain.retrospect;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class RetrospectId {
    private final Long id;

    public RetrospectId(Long id) {
        this.id = id;
    }
}
