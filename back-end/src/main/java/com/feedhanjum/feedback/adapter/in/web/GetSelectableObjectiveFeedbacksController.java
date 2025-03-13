package com.feedhanjum.feedback.adapter.in.web;

import com.feedhanjum.feedback.application.port.in.GetSelectableObjectiveFeedbacksUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
class GetSelectableObjectiveFeedbacksController {
    private final GetSelectableObjectiveFeedbacksUseCase getSelectableObjectiveFeedbacksUseCase;

    @Operation(summary = "객관식 피드백 선택지 조회", description = "사용자에게 객관식 피드백 선택지를 제공하기 위한 API")
    @ApiResponse(responseCode = "200", description = "선택 가능한 객관식 피드백 정보를 반환한다.")
    @GetMapping("/api/feedback/objective")
    public ResponseEntity<Map<String, Map<String, List<String>>>> getSelectableObjectFeedbacks() {
        var objectiveFeedbacksMap = getSelectableObjectiveFeedbacksUseCase.getSelectableObjectiveFeedbacks();
        return ResponseEntity.ok(objectiveFeedbacksMap);
    }
}
