package com.feedhanjum.back_end.feedback.adapter.in.web;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.application.port.in.LikeFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.LikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
class LikeFeedbackController {
    private final LikeFeedbackUseCase likeFeedbackUseCase;

    @Operation(summary = "피드백 좋아요", description = "피드백에 좋아요를 누릅니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드백 좋아요 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우")
    })
    @PostMapping("/api/member/{memberId}/feedbacks/{feedbackId}/liked")
    public ResponseEntity<Void> likeFeedback(@Login Long loginId, @PathVariable Long memberId, @PathVariable Long feedbackId) {
        if (!Objects.equals(loginId, memberId)) {
            throw new SecurityException("Only the owner can like the feedback");
        }
        var command = new LikeFeedbackCommand(new FeedbackId(feedbackId), memberId);
        likeFeedbackUseCase.likeFeedback(command);
        return ResponseEntity.noContent().build();
    }
}
