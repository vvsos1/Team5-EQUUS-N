package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.feedback.application.port.in.GetSelectableFeedbackPreferencesUseCase;
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
public class GetSelectableFeedbackPreferencesController {
    private final GetSelectableFeedbackPreferencesUseCase getSelectableFeedbackPreferencesUseCase;

    @Operation(summary = "피드백 선호도 선택지 조회", description = "사용자에게 피드백 선호도 선택지를 제공하기 위한 API")
    @ApiResponse(responseCode = "200", description = "선택 가능한 피드백 선호 정보를 반환한다.")
    @GetMapping("/api/feedback/preference")
    public ResponseEntity<Map<String, List<String>>> getSelectableFeedbackPreference() {
        var preferences = getSelectableFeedbackPreferencesUseCase.getSelectableFeedbackPreferences();
        return ResponseEntity.ok(preferences);
    }
}
