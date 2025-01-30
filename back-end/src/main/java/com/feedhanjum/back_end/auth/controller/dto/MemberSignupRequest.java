package com.feedhanjum.back_end.auth.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignupRequest {

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상, 20자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "활동명을 입력해주세요.")
    @Size(min = 1, max = 20, message = "활동명은 1자 이상, 20자 이하여야 합니다.")
    private String name;
}