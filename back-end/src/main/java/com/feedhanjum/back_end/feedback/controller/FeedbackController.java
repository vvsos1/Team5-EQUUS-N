package com.feedhanjum.back_end.feedback.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackRequestForApiRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.RegularFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.exception.NoRegularFeedbackRequestException;
import com.feedhanjum.back_end.feedback.service.FeedbackQueryService;
import com.feedhanjum.back_end.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackQueryService feedbackQueryService;

    @ApiResponse(responseCode = "204", description = "수시 피드백 전송 성공", useReturnTypeSchema = true)
    @PostMapping("/feedbacks/frequent")
    public ResponseEntity<Void> sendFrequentFeedback(@Login Long senderId,
                                                     @Valid @RequestBody FrequentFeedbackSendRequest request) {
        feedbackService.sendFrequentFeedback(senderId, request.receiverId(), request.teamId(),
                request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED
                , request.feedbackFeeling(), request.objectiveFeedbacks(), request.subjectiveFeedback());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "정기 피드백 전송", description = "정기 피드백을 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "정기 피드백 전송 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "선행되는 정기 피드백 요청이 없을 경우", useReturnTypeSchema = true)
    })
    @PostMapping("/feedbacks/regular")
    public ResponseEntity<Void> sendRegularFeedback(@Login Long senderId,
                                                    @Valid @RequestBody RegularFeedbackSendRequest request) {
        try {
            feedbackService.sendRegularFeedback(senderId, request.receiverId(), request.scheduleId(),
                    request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED
                    , request.feedbackFeeling(), request.objectiveFeedbacks(), request.subjectiveFeedback());
        } catch (NoRegularFeedbackRequestException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "202", description = "수시 피드백 요청 성공", useReturnTypeSchema = true)
    @PostMapping("/feedbacks/frequent/request")
    public ResponseEntity<Void> requestFrequentFeedback(@Login Long senderId,
                                                        @Valid @RequestBody FrequentFeedbackRequestForApiRequest request) {
        feedbackService.requestFrequentFeedback(senderId, request.teamId(),
                request.receiverId(), request.requestedContent());
        return ResponseEntity.accepted().build();
    }
}
