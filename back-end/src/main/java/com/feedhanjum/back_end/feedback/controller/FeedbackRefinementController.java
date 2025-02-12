package com.feedhanjum.back_end.feedback.controller;

import com.feedhanjum.back_end.feedback.controller.dto.request.FeedbackRefineRequest;
import com.feedhanjum.back_end.feedback.controller.dto.response.FeedbackRefineResponse;
import com.feedhanjum.back_end.feedback.controller.dto.response.RefineRemainCountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback-refinement")
public class FeedbackRefinementController {

    @Operation(summary = "주관식 피드백 다듬기", description = "AI를 활용해 주관식 피드백을 다듬습니다. 호출 가능 횟수 제한은 3회입니다. 피드백 작성 시 호출 가능 횟수 제한이 초기화됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주관식 피드백 다듬기 성공"),
            @ApiResponse(responseCode = "429", description = "호출 가능 횟수 초과", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackRefineResponse> refineFeedback(@Valid @RequestBody FeedbackRefineRequest request) {
        return ResponseEntity.ok(new FeedbackRefineResponse("테스트 데이터입니다", 3));
    }

    @Operation(summary = "남은 호출 가능 횟수 조회", description = "주관식 피드백 다듬기 호출 가능 횟수를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "남은 호출 가능 횟수 조회 성공")
    })
    @GetMapping(value = "/remain-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefineRemainCountResponse> getRemainCount() {
        return ResponseEntity.ok().build();
    }
}
