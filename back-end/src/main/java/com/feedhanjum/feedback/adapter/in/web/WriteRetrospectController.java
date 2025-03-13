package com.feedhanjum.feedback.adapter.in.web;


import com.feedhanjum.auth.infra.Login;
import com.feedhanjum.feedback.adapter.in.web.dto.request.RetrospectWriteRequest;
import com.feedhanjum.feedback.application.port.in.retrospect.WriteRetrospectUseCase;
import com.feedhanjum.feedback.application.port.in.retrospect.command.WriteRetrospectCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class WriteRetrospectController {
    private final WriteRetrospectUseCase writeRetrospectUseCase;

    @Operation(summary = "회고 작성하기", description = "회고를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회고 작성 성공", content = @Content),
            @ApiResponse(responseCode = "403", description = "본인이 아닌 경우", content = @Content)
    })
    @PostMapping(value = "/api/retrospect", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> writeRetrospect(@Login Long loginId,
                                                @Valid @RequestBody RetrospectWriteRequest request) {
        if (!Objects.equals(loginId, request.writerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = new WriteRetrospectCommand(request.title(), request.content(), request.writerId(), request.teamId());
        writeRetrospectUseCase.writeRetrospect(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
