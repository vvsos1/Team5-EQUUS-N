package com.feedhanjum.feedback.domain.feedback;

public enum FeedbackType {
    ANONYMOUS, IDENTIFIED;

    public boolean isAnonymous() {
        return this == ANONYMOUS;
    }
}
