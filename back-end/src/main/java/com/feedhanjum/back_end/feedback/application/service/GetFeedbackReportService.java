package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackReportPort;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
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
