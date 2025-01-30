package com.feedhanjum.back_end.auth.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private Long userId;
    private String email;
}