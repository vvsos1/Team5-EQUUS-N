package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.CreateFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.CreateFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackReportPort;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
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
