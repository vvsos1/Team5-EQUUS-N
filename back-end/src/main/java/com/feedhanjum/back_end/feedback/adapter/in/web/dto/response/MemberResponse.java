package com.feedhanjum.back_end.feedback.adapter.in.web.dto.response;

import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberResponse(
        @Schema(description = "회원의 ID")
        Long id,

        @Schema(description = "회원의 활동 이름")
        String name,

        @Schema(description = "회원이 회원가입 시 사용했던 이메일")
        String email,

        @Schema(description = "회원의 프로필 이미지")
        ProfileImage profileImage
) {
    public static MemberResponse from(FeedbackMember member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getProfileImage());

    }
}