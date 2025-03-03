package com.feedhanjum.back_end.feedback.adapter.in.web;


import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.SendRegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.application.port.in.SendRegularFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.in.SendRegularFeedbackUseCase;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class SendRegularFeedbackController {
    private final SendRegularFeedbackUseCase sendRegularFeedbackUseCase;

    @Operation(summary = "정기 피드백 전송", description = "일정별로 정기 피드백을 전송합니다. 정기 피드백 요청을 통해 피드백 작성을 요청받았어야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "정기 피드백 전송 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "선행되는 정기 피드백 요청이 없을 경우", useReturnTypeSchema = true)
    })
    @PostMapping("/api/feedbacks/regular")
    public ResponseEntity<Void> sendFrequentFeedback(@Login Long senderId,
                                                     @Valid @RequestBody SendRegularFeedbackRequest request) {

        var command = new SendRegularFeedbackCommand(
                senderId,
                request.receiverId(),
                request.scheduleId(),
                request.isAnonymous() ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED,
                request.feedbackFeeling(),
                request.objectiveFeedbacks(),
                request.subjectiveFeedback()
        );

        sendRegularFeedbackUseCase.sendRegularFeedback(command);
        return ResponseEntity.noContent().build();
    }

}
