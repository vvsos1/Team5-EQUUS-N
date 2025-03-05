package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.SendFrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.application.port.in.SendFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.SendFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class SendFrequentFeedbackController {
    private final SendFrequentFeedbackUseCase sendFrequentFeedbackUseCase;

    @Operation(summary = "수시 피드백 전송", description = "팀별로 수시 피드백을 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "수시 피드백 전송 성공. 연관된 수시 피드백 요청도 함께 삭제", useReturnTypeSchema = true)
    })
    @PostMapping("/api/feedbacks/frequent")
    public ResponseEntity<Void> sendFrequentFeedback(@Login Long senderId,
                                                     @Valid @RequestBody SendFrequentFeedbackRequest request) {

        var command = new SendFrequentFeedbackCommand(
                senderId,
                request.receiverId(),
                request.teamId(),
                request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED,
                request.feedbackFeeling(),
                request.objectiveFeedbacks(),
                request.subjectiveFeedback()
        );

        sendFrequentFeedbackUseCase.sendFrequentFeedback(command);
        return ResponseEntity.noContent().build();
    }

}
