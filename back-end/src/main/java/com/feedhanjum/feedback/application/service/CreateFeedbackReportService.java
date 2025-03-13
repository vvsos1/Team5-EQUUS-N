package com.feedhanjum.feedback.application.service;

import com.feedhanjum.feedback.application.port.in.report.CreateFeedbackReportUseCase;
import com.feedhanjum.feedback.application.port.in.report.command.CreateFeedbackReportCommand;
import com.feedhanjum.feedback.application.port.out.feedback.SaveFeedbackReportPort;
import com.feedhanjum.feedback.domain.FeedbackReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class CreateFeedbackReportService implements CreateFeedbackReportUseCase {
    private final SaveFeedbackReportPort saveFeedbackReportPort;

    @Override
    @Transactional
    public void createFeedbackReport(CreateFeedbackReportCommand command) {
        var report = FeedbackReport.createNewReport(command.getMemberId());
        saveFeedbackReportPort.save(report);
    }
}
