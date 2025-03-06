package com.feedhanjum.back_end.feedback.application.port.out.feedback;

import java.util.Optional;

public interface FeedbackReportLockManager {

    Optional<FeedbackReportLock> lock(Long memberId);

    void unlock(FeedbackReportLock lock);
}
