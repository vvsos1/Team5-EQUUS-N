package com.feedhanjum.feedback.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Order(1)
@RestControllerAdvice(basePackages = "com.feedhanjum.feedback")
public class FeedbackControllerAdvice {
    /**
     * 선행되야 하는 정기 피드백 요청이 없는데 정기 피드백을 보낼 경우
     */
    @ExceptionHandler(RegularFeedbackRequestNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoRegularFeedbackRequest(RegularFeedbackRequestNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "NO_REGULAR_FEEDBACK_REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
