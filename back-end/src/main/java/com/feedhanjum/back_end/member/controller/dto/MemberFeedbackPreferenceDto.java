package com.feedhanjum.back_end.member.controller.dto;

import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MemberFeedbackPreferenceDto(
        @Schema(description = "회원의 ID")
        Long id,

        @Schema(description = "회원의 활동 이름")
        String name,

        @Schema(description = "회원이 회원가입 시 사용했던 이메일")
        String email,

        @Schema(description = "회원의 프로필 이미지")
        ProfileImage profileImage,

        @Schema(description = "회원의 피드백 선호 정보")
        List<FeedbackPreference> feedbackPreferences
) {
    public MemberFeedbackPreferenceDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getProfileImage(), member.getFeedbackPreferences().stream().toList());
    }
}
