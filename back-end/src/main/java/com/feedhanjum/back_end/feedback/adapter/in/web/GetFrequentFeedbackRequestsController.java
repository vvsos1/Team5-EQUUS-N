package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.FrequentFeedbackRequestQueryRequest;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.response.FrequentFeedbackRequestResponse;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.GetFrequentFeedbackRequestsUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.GetFrequentFeedbackRequestsCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetFrequentFeedbackRequestsController {
    private final GetFrequentFeedbackRequestsUseCase getFrequentFeedbackRequestsUseCase;

    @Operation(summary = "수시 피드백 요청 조회", description = "팀별 수시 피드백 요청을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀별 수시 피드백 요청 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/api/feedbacks/frequent/request")
    public ResponseEntity<List<FrequentFeedbackRequestResponse>> getFrequentFeedbackRequest(@Login Long receiverId,
                                                                                            @ParameterObject @Valid FrequentFeedbackRequestQueryRequest request) {
        var command = new GetFrequentFeedbackRequestsCommand(request.teamId(), receiverId);
        var requests = getFrequentFeedbackRequestsUseCase.getFrequentFeedbackRequests(command);
        var responses = requests.stream().map(FrequentFeedbackRequestResponse::from).toList();
        return ResponseEntity.ok(responses);
    }
}
