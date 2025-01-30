package com.feedhanjum.back_end.auth.controller;

import com.feedhanjum.back_end.auth.controller.dto.MemberSignupRequest;
import com.feedhanjum.back_end.auth.controller.dto.MemberSignupResponse;
import com.feedhanjum.back_end.auth.controller.mapper.MemberMapper;
import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService memberDetailsService;
    private final MemberMapper memberMapper;

    /**
     * 회원가입을 처리하는 핸들러
     * @param request 
     * @return 회원 가입 성공 여부
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponse> signup(@Valid @RequestBody MemberSignupRequest request) {
        MemberDetails member = memberMapper.toEntity(request);
        String name = request.getName();

        MemberDetails savedMember = memberDetailsService.registerMember(member, name);

        MemberSignupResponse response = memberMapper.toResponse(savedMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}