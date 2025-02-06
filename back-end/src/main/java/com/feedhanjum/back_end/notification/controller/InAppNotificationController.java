package com.feedhanjum.back_end.notification.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.notification.controller.dto.MultipleNotificationReadRequest;
import com.feedhanjum.back_end.notification.controller.dto.UnreadNotificationResponse;
import com.feedhanjum.back_end.notification.controller.dto.notification.InAppNotificationDto;
import com.feedhanjum.back_end.notification.service.InAppNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class InAppNotificationController {
    private final InAppNotificationService inAppNotificationService;

    @Operation(summary = "알림 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알림 조회 성공"),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @GetMapping(value = "/receiver/{receiverId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InAppNotificationDto>> getNotification(@Login Long loginId, @PathVariable Long receiverId) {
        if (!Objects.equals(loginId, receiverId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InAppNotificationDto> notifications = inAppNotificationService.getInAppNotifications(receiverId);
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "안읽은 알림 존재여부 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "안읽은 알림 존재여부 조회 성공"),
    })
    @GetMapping(value = "/receiver/{receiverId}/unread", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UnreadNotificationResponse> checkUnreadNotification(@PathVariable Long receiverId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "여러 알림 일괄 읽음 처리")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "알림 읽음 처리 성공"),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @PostMapping(value = "/receiver/{receiverId}/mark-as-read", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> readNotifications(@Login Long loginId, @PathVariable Long receiverId, @Valid @RequestBody MultipleNotificationReadRequest readRequest) {
        if (!Objects.equals(loginId, receiverId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        inAppNotificationService.readInAppNotifications(receiverId, readRequest.notificationIds());
        return ResponseEntity.noContent().build();
    }


}
