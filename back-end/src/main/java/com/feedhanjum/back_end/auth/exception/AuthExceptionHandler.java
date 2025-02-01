package com.feedhanjum.back_end.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 이메일이 존재하는 경우
     * @param e
     * @return
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> emailAlreadyExists(EmailAlreadyExistsException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "EMAIL_ALREADY_EXISTS");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * 아이디 혹은 비밀번호가 일치하지 않아, 자격 증명에 실패했을 경우
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> invalidCredentials(InvalidCredentialsException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "INVALID_CREDENTIALS");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * 사용자가 로그인 상태가 아닐 경우
     *
     * @param e
     * @return
     */
    @ExceptionHandler(LoginStateRequiredException.class)
    public ResponseEntity<Map<String, String>> loginStateRequired(LoginStateRequiredException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "LOGIN_STATE_REQUIRED");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
