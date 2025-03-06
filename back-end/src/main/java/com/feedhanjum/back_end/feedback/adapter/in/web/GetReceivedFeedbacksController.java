package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.core.dto.Paged;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.ReceivedFeedbacksQueryRequest;
import com.feedhanjum.back_end.feedback.application.port.in.GetReceivedFeedbacksUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetReceivedFeedbacksCommand;
import com.feedhanjum.back_end.feedback.dto.ReceivedFeedbackDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class GetReceivedFeedbacksController {
    private final GetReceivedFeedbacksUseCase getReceivedFeedbacksUseCase;

    @Operation(summary = "받은 피드백 조회하기", description = "받은 피드백을 조회힙니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "보낸 피드백 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @GetMapping("/api/feedbacks/receiver/{receiverId}")
    public ResponseEntity<Paged<ReceivedFeedbackDto>> getReceivedFeedbacks(@Login Long loginId,
                                                                           @PathVariable Long receiverId,
                                                                           @ParameterObject @Valid ReceivedFeedbacksQueryRequest request) {
        if (!Objects.equals(loginId, receiverId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = new GetReceivedFeedbacksCommand(receiverId, request.teamId(), request.filterHelpful(), request.page(), request.sortOrder());
        Page<ReceivedFeedbackDto> receivedFeedbacks = getReceivedFeedbacksUseCase.getReceivedFeedbacks(command);
        return ResponseEntity.ok(Paged.from(receivedFeedbacks));
    }
}
