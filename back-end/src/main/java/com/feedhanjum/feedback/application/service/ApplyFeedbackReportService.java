package com.feedhanjum.feedback.application.service;

import com.feedhanjum.feedback.application.port.in.report.ApplyFeedbackReportUseCase;
import com.feedhanjum.feedback.application.port.in.report.command.ApplyFeedbackReportCommand;
import com.feedhanjum.feedback.application.port.out.feedback.*;
import com.feedhanjum.feedback.domain.FeedbackReport;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class ApplyFeedbackReportService implements ApplyFeedbackReportUseCase {
    private final LoadFeedbackPort loadFeedbackPort;
    private final FeedbackReportLockManager feedbackReportLockManager;
    private final LoadFeedbackReportPort loadFeedbackReportPort;
    private final SaveFeedbackReportPort saveFeedbackReportPort;

    @Override
    @Transactional
    public void applyFeedbackReport(ApplyFeedbackReportCommand command) {
        FeedbackReportLock lock = feedbackReportLockManager.lock(command.getMemberId()).orElseThrow();

        FeedbackId feedbackId = command.getFeedbackId();
        Feedback feedback = loadFeedbackPort.loadFeedback(feedbackId).orElseThrow();

        FeedbackReport report = loadFeedbackReportPort.load(command.getMemberId()).orElseThrow();
        report.applyFeedback(feedback);

        saveFeedbackReportPort.save(report);
        feedbackReportLockManager.unlock(lock);
    }
}
