package com.feedhanjum.feedback.adapter.in.web;

import com.feedhanjum.auth.infra.Login;
import com.feedhanjum.core.dto.Paged;
import com.feedhanjum.feedback.adapter.in.web.dto.request.SentFeedbacksQueryRequest;
import com.feedhanjum.feedback.application.port.in.feedback.GetSentFeedbacksUseCase;
import com.feedhanjum.feedback.application.port.in.feedback.command.GetSentFeedbacksCommand;
import com.feedhanjum.feedback.dto.SentFeedbackDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
class GetSentFeedbacksController {
    private final GetSentFeedbacksUseCase getSentFeedbacksUseCase;

    @Operation(summary = "보낸 피드백 조회하기", description = "받은 피드백을 조회힙니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "보낸 피드백 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @GetMapping("/api/feedbacks/sender/{senderId}")
    public ResponseEntity<Paged<SentFeedbackDto>> getSentFeedbacks(@Login Long loginId,
                                                                   @PathVariable Long senderId,
                                                                   @ParameterObject @Valid SentFeedbacksQueryRequest request) {
        if (!Objects.equals(loginId, senderId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var command = new GetSentFeedbacksCommand(senderId, request.teamId(), request.filterHelpful(), request.page(), request.sortOrder());
        var sentFeedbacks = getSentFeedbacksUseCase.getSentFeedbacks(command);
        return ResponseEntity.ok(Paged.from(sentFeedbacks));
    }
}
