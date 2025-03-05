package com.feedhanjum.back_end.feedback.domain.feedback;

public enum FeedbackType {
    ANONYMOUS, IDENTIFIED;

    public boolean isAnonymous() {
        return this == ANONYMOUS;
    }
}
