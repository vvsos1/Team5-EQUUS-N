package com.feedhanjum.back_end.auth.controller.dto;


import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GoogleSignupRequest(
        @Schema(description = "구글측에서 발급받은 인증 관련 코드")
        String code,

        @Schema(description = "사용자가 사용할 프로필 이미지")
        ProfileImage profileImage,

        @Schema(description = "사용자의 피드백 선호 정보")
        @NotNull
        @Size(min = 2)
        List<FeedbackPreference> feedbackPreference
) {
}
