package com.feedhanjum.back_end.feedback.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackRequestForApiRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackRequestQueryRequest;
import com.feedhanjum.back_end.feedback.controller.dto.response.FeedbackReportDto;
import com.feedhanjum.back_end.feedback.controller.dto.response.FrequentFeedbackRequestForApiResponse;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
import com.feedhanjum.back_end.feedback.domain.feedback.ObjectiveFeedback;
import com.feedhanjum.back_end.feedback.service.FeedbackQueryService;
import com.feedhanjum.back_end.feedback.service.FeedbackService;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackQueryService feedbackQueryService;

    @Operation(summary = "수시 피드백 요청", description = "수시 피드백을 요청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "수시 피드백 요청 성공", useReturnTypeSchema = true)
    })
    @PostMapping("/feedbacks/frequent/request")
    public ResponseEntity<Void> requestFrequentFeedback(@Login Long senderId,
                                                        @Valid @RequestBody FrequentFeedbackRequestForApiRequest request) {
        feedbackService.requestFrequentFeedback(senderId, request.teamId(),
                request.receiverId(), request.requestedContent());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "수시 피드백 요청 조회", description = "팀별 수시 피드백 요청을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀별 수시 피드백 요청 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/feedbacks/frequent/request")
    public ResponseEntity<List<FrequentFeedbackRequestForApiResponse>> getFrequentFeedbackRequest(@Login Long receiverId,
                                                                                                  @ParameterObject @Valid FrequentFeedbackRequestQueryRequest request) {
        List<FrequentFeedbackRequestForApiResponse> frequentFeedbackRequests = feedbackQueryService.getFrequentFeedbackRequests(receiverId, request.teamId());
        return ResponseEntity.ok(frequentFeedbackRequests);
    }

    @Operation(summary = "피드백 선호도 선택지 조회", description = "사용자에게 피드백 선호도 선택지를 제공하기 위한 API")
    @ApiResponse(responseCode = "200", description = "선택 가능한 피드백 선호 정보를 반환한다.")
    @GetMapping("/feedback/preference")
    public ResponseEntity<Map<String, List<String>>> getSelectableFeedbackPreference() {
        Map<String, List<String>> feedbackPreferenceMap = new HashMap<>();
        for (FeedbackPreference feedbackPreference : FeedbackPreference.values()) {
            List<String> descriptions = feedbackPreferenceMap.computeIfAbsent(feedbackPreference.getType(), key -> new ArrayList<>());
            descriptions.add(feedbackPreference.getDescription());
        }
        return ResponseEntity.ok(feedbackPreferenceMap);
    }

    @Operation(summary = "객관식 피드백 선택지 조회", description = "사용자에게 객관식 피드백 선택지를 제공하기 위한 API")
    @ApiResponse(responseCode = "200", description = "선택 가능한 객관식 피드백 정보를 반환한다.")
    @GetMapping("/feedback/objective")
    public ResponseEntity<Map<String, Map<String, List<String>>>> getSelectableObjectFeedbacks() {
        Map<String, Map<String, List<String>>> objectiveFeedbacksMap = new HashMap<>();
        for (ObjectiveFeedback objectiveFeedback : ObjectiveFeedback.values()) {
            Map<String, List<String>> objectiveFeedbackFeelingMap = objectiveFeedbacksMap.computeIfAbsent(objectiveFeedback.getFeeling().getDescription(), key -> new HashMap<>());
            List<String> objectiveFeedbackDescriptions = objectiveFeedbackFeelingMap.computeIfAbsent(objectiveFeedback.getCategory().getDescription(), key -> new ArrayList<>());
            objectiveFeedbackDescriptions.add(objectiveFeedback.getDescription());
        }
        return ResponseEntity.ok(objectiveFeedbacksMap);
    }

    @Operation(summary = "피드백 리포트 조회", description = "로그인 유저의 피드백 리포트를 조회힙니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드백 리포트 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/feedbacks/report")
    public ResponseEntity<FeedbackReportDto> getFeedbackReport(@Login Long receiverId) {
        FeedbackReport feedbackReport = feedbackQueryService.getFeedbackReport(receiverId);
        return ResponseEntity.ok(FeedbackReportDto.from(feedbackReport));
    }
}
