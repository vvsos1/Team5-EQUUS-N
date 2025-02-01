package com.feedhanjum.back_end.auth.controller.dto;

public record MemberSignupResponse(
        Long id,
        String email,
        String message
) {
}
