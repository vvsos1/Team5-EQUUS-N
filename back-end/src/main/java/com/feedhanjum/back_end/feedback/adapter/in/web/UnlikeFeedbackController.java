package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.application.port.in.UnlikeFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.UnlikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.domain.FeedbackId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class UnlikeFeedbackController {
    private final UnlikeFeedbackUseCase unlikeFeedbackUseCase;

    @Operation(summary = "피드백 좋아요 취소", description = "피드백에 누른 좋아요를 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드백 좋아요 취소 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우")
    })
    @DeleteMapping("/api/member/{memberId}/feedbacks/{feedbackId}/liked")
    public ResponseEntity<Void> likeFeedback(@Login Long loginId, @PathVariable Long memberId, @PathVariable Long feedbackId) {
        if (!Objects.equals(loginId, memberId)) {
            throw new SecurityException("Only the owner can unlike the feedback");
        }
        var command = new UnlikeFeedbackCommand(new FeedbackId(feedbackId), memberId);
        unlikeFeedbackUseCase.unlikeFeedback(command);
        return ResponseEntity.noContent().build();
    }
}
