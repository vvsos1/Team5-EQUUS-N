package com.feedhanjum.feedback.adapter.in.web;

import com.feedhanjum.auth.infra.Login;
import com.feedhanjum.feedback.adapter.in.web.dto.response.FeedbackReportResponse;
import com.feedhanjum.feedback.application.port.in.report.GetFeedbackReportUseCase;
import com.feedhanjum.feedback.application.port.in.report.command.GetFeedbackReportCommand;
import com.feedhanjum.feedback.domain.FeedbackReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GetFeedbackReportController {

    private final GetFeedbackReportUseCase getFeedbackReportUseCase;

    @Operation(summary = "피드백 리포트 조회", description = "로그인 유저의 피드백 리포트를 조회힙니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드백 리포트 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/api/feedbacks/report")
    public ResponseEntity<FeedbackReportResponse> getFeedbackReport(@Login Long receiverId) {
        var command = new GetFeedbackReportCommand(receiverId);
        FeedbackReport feedbackReport = getFeedbackReportUseCase.getFeedbackReport(command)
                .orElseThrow();
        return ResponseEntity.ok(FeedbackReportResponse.from(feedbackReport));
    }
}
