package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.RegularFeedbackRequestQueryRequest;
import com.feedhanjum.back_end.feedback.application.port.in.request.regular.SkipRegularFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.SkipRegularFeedbackRequestCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class SkipRegularFeedbackRequestController {

    private final SkipRegularFeedbackRequestUseCase skipRegularFeedbackRequestUseCase;

    @Operation(summary = "정기 피드백 건너뛰기", description = "해당 일정 정기 피드백을 건너뛰기합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "건너뛰기 성공", useReturnTypeSchema = true)
    })
    @DeleteMapping("/api/feedbacks/regular/request")
    public ResponseEntity<Void> skipRegularFeedbackRequest(@Login Long receiverId,
                                                           @ParameterObject @Valid RegularFeedbackRequestQueryRequest request) {
        var command = new SkipRegularFeedbackRequestCommand(request.scheduleId(), receiverId);
        skipRegularFeedbackRequestUseCase.skipRegularFeedbackRequest(command);
        return ResponseEntity.noContent().build();
    }
}
