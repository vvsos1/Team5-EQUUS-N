package com.feedhanjum.back_end.feedback.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.core.dto.Paged;
import com.feedhanjum.back_end.feedback.controller.dto.request.RetrospectsQueryRequest;
import com.feedhanjum.back_end.feedback.controller.dto.response.RetrospectResponseDto;
import com.feedhanjum.back_end.feedback.domain.Retrospect;
import com.feedhanjum.back_end.feedback.service.RetrospectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/retrospect")
public class RetrospectController {
    private final RetrospectService retrospectService;


    @Operation(summary = "회고 조회하기", description = "회고를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회고 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @GetMapping("/{writerId}")
    public ResponseEntity<Paged<RetrospectResponseDto>> getRetrospects(@Login Long loginId,
                                                                       @PathVariable Long writerId,
                                                                       @ParameterObject @Valid RetrospectsQueryRequest request) {
        if (!Objects.equals(loginId, writerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Page<Retrospect> retrospects = retrospectService.getRetrospects(writerId, request.teamId(), request.page(), request.sortOrder());
        return ResponseEntity.ok(Paged.from(retrospects.map(RetrospectResponseDto::from)));
    }

}
