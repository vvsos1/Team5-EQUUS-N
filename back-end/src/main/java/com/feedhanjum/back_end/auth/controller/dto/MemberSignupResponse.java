package com.feedhanjum.back_end.auth.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupResponse {
    private Long id;
    private String email;
    private String message;

}