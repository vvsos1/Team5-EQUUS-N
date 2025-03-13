package com.feedhanjum.feedback.adapter.in.web.dto.request;

import com.feedhanjum.core.constraints.ByteLength;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.feedback.domain.feedback.ObjectiveFeedback;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public record SendRegularFeedbackRequest(
        @Schema(description = "정기 피드백을 받을 사람의 ID")
        @NotNull
        Long receiverId,

        @Schema(description = "연관된 일정 ID")
        @NotNull
        Long scheduleId,

        @Schema(description = "피드백의 방향성")
        @NotNull
        FeedbackFeeling feedbackFeeling,

        @Schema(description = "객관식 피드백 목록")
        @Size(min = Feedback.MIN_OBJECTIVE_FEEDBACK_SIZE, max = Feedback.MAX_OBJECTIVE_FEEDBACK_SIZE)
        List<ObjectiveFeedback> objectiveFeedbacks,

        @Schema(description = "주관식 피드백")
        @ByteLength(min = Feedback.MIN_SUBJECTIVE_FEEDBACK_BYTE, max = Feedback.MAX_SUBJECTIVE_FEEDBACK_BYTE, strip = true)
        String subjectiveFeedback,

        @Schema(description = "익명 여부")
        @NotNull
        Boolean isAnonymous
) {
}
