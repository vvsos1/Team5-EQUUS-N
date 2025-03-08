package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.RequestFrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.RequestFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RequestFrequentFeedbackCommand;
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
public class RequestFrequentFeedbackController {
    private final RequestFrequentFeedbackUseCase requestFrequentFeedbackUseCase;

    @Operation(summary = "수시 피드백 요청", description = "수시 피드백을 요청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "수시 피드백 요청 성공", useReturnTypeSchema = true)
    })
    @PostMapping("/api/feedbacks/frequent/request")
    public ResponseEntity<Void> requestFrequentFeedback(@Login Long senderId,
                                                        @Valid @RequestBody RequestFrequentFeedbackRequest request) {
        var command = new RequestFrequentFeedbackCommand(senderId, request.teamId(), request.receiverId(), request.requestedContent());
        requestFrequentFeedbackUseCase.requestFrequentFeedback(command);
        return ResponseEntity.accepted().build();
    }
}
