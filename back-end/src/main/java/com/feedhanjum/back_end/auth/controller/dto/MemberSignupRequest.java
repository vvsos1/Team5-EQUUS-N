package com.feedhanjum.back_end.auth.controller.dto;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.core.constraints.ByteLength;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = MemberDetails.MIN_PASSWORD_LENGTH, max = MemberDetails.MAX_PASSWORD_LENGTH, message = "비밀번호는 최소 {min}자 이상, {max}자 이하여야 합니다.")
        String password,

        @NotBlank(message = "활동명을 입력해주세요.")
        @ByteLength(min = MemberDetails.MIN_NAME_BYTE, max = MemberDetails.MAX_NAME_BYTE, message = "활동명은 {min}자 이상, {max}자 이하여야 합니다.")
        String name,

        ProfileImage profileImage
) {
}
