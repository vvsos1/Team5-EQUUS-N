package com.feedhanjum.feedback.application.service;

import com.feedhanjum.feedback.application.port.in.report.GetFeedbackReportUseCase;
import com.feedhanjum.feedback.application.port.in.report.command.GetFeedbackReportCommand;
import com.feedhanjum.feedback.application.port.out.feedback.LoadFeedbackReportPort;
import com.feedhanjum.feedback.domain.FeedbackReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class GetFeedbackReportService implements GetFeedbackReportUseCase {
    private final LoadFeedbackReportPort loadFeedbackReportPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackReport> getFeedbackReport(GetFeedbackReportCommand command) {
        return loadFeedbackReportPort.load(command.getMemberId());
    }
}
