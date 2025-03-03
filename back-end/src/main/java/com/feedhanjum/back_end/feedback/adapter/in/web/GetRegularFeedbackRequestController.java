package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.RegularFeedbackRequestQueryRequest;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.response.RegularFeedbackRequestResponse;
import com.feedhanjum.back_end.feedback.application.port.in.GetRegularFeedbackRequestListUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.GetRegularFeedbackRequestListCommand;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
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
public class GetRegularFeedbackRequestController {
    private final GetRegularFeedbackRequestListUseCase getRegularFeedbackRequestListUseCase;

    @Operation(summary = "정기 피드백 요청 조회", description = "일정별 정기 피드백 요청을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일정별 정기 피드백 요청 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/api/feedbacks/regular/request")
    public ResponseEntity<List<RegularFeedbackRequestResponse>> getRegularFeedbackRequest(@Login Long receiverId,
                                                                                          @ParameterObject @Valid RegularFeedbackRequestQueryRequest request) {
        var command = new GetRegularFeedbackRequestListCommand(request.scheduleId(), receiverId);
        List<RegularFeedbackRequest> requests = getRegularFeedbackRequestListUseCase.getRegularFeedbackRequestList(command);
        return ResponseEntity.ok(requests.stream().map(RegularFeedbackRequestResponse::from).toList());
    }
}
