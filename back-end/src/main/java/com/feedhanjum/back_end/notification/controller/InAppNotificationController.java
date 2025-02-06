package com.feedhanjum.back_end.notification.controller;

import com.feedhanjum.back_end.notification.controller.dto.UnreadNotificationResponse;
import com.feedhanjum.back_end.notification.controller.dto.notification.InAppNotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class InAppNotificationController {

    @Operation(summary = "알림 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알림 조회 성공"),
    })
    @GetMapping(value = "/receiver/{receiverId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<InAppNotificationDto> getNotification(@PathVariable Long receiverId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "안읽은 알림 존재여부 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "안읽은 알림 존재여부 조회 성공"),
    })
    @GetMapping(value = "/receiver/{receiverId}/unread", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UnreadNotificationResponse> checkUnreadNotification(@PathVariable Long receiverId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알림 읽음 처리")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "알림 읽음 처리 성공"),
    })
    @PutMapping(value = "/{notificationId}/read", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> readNotification(@PathVariable Long notificationId) {
        return ResponseEntity.ok().build();
    }


}
