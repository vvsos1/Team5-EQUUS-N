package com.feedhanjum.back_end.member.controller.dto;

import com.feedhanjum.back_end.member.domain.ProfileImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileChangeRequest (
        @NotBlank(message = "활동명을 입력해주세요.")
        @Size(min = 1, max = 20, message = "활동명은 1자 이상, 20자 이하여야 합니다.")
        String name,
        ProfileImage profileImage
){
}
