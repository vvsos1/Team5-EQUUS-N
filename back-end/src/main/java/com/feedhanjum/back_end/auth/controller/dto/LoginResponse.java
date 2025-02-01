package com.feedhanjum.back_end.auth.controller.dto;

public record LoginResponse(
        String message,
        Long userId,
        String email
) {
}
