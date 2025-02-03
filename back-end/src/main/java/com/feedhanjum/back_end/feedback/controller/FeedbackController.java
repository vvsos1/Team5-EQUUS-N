package com.feedhanjum.back_end.feedback.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.RegularFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.service.FeedbackQueryService;
import com.feedhanjum.back_end.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(description = "수시 피드백 전송 성공")
    @PostMapping("/feedbacks/frequent")
    public ResponseEntity<Void> sendFrequentFeedback(@Login Long senderId,
                                                     @Valid @RequestBody FrequentFeedbackSendRequest request) {
        feedbackService.sendFrequentFeedback(senderId, request.receiverId(), request.teamId(),
                request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED
                , request.feedbackFeeling(), request.objectiveFeedbacks(), request.subjectiveFeedback());
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(description = "정기 피드백 전송 성공")
    @PostMapping("/feedbacks/regular")
    public ResponseEntity<Void> sendRegularFeedback(@Login Long senderId,
                                                    @Valid @RequestBody RegularFeedbackSendRequest request) {
        feedbackService.sendRegularFeedback(senderId, request.receiverId(), request.scheduleId(),
                request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED
                , request.feedbackFeeling(), request.objectiveFeedbacks(), request.subjectiveFeedback());
        return ResponseEntity.noContent().build();
    }
}
