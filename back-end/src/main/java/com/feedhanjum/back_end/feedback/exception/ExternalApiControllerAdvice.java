package com.feedhanjum.back_end.feedback.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@Slf4j
@RestControllerAdvice
public class ExternalApiControllerAdvice {

    @ExceptionHandler(AiRefineChanceAlreadyUsedException.class)
    public ResponseEntity<String> handleAiRefineChanceAlreadyUsedException(AiRefineChanceAlreadyUsedException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
    }

    @ExceptionHandler(ApiResponseFailException.class)
    public ResponseEntity<String> handleApiResponseFailException(ApiResponseFailException e) {
        log.warn("API 호출 실패: ", e.getCause());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }
}
