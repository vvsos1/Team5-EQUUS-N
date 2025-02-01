package com.feedhanjum.back_end.auth.controller;

import com.feedhanjum.back_end.auth.controller.dto.LoginRequest;
import com.feedhanjum.back_end.auth.controller.dto.LoginResponse;
import com.feedhanjum.back_end.auth.controller.dto.MemberSignupRequest;
import com.feedhanjum.back_end.auth.controller.dto.MemberSignupResponse;
import com.feedhanjum.back_end.auth.controller.mapper.MemberMapper;
import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.infra.SessionConst;
import com.feedhanjum.back_end.auth.service.AuthService;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    private final AuthService authService;
    private final MemberMapper memberMapper;

    /**
     * 회원가입을 처리하는 핸들러
     * @param request 
     * @return 회원 가입 성공 여부
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponse> signup(@Valid @RequestBody MemberSignupRequest request) {
        MemberDetails member = memberMapper.toEntity(request);
        String name = request.name();
        ProfileImage profileImage = request.profileImage();

        MemberDetails savedMember = authService.registerMember(member, name, profileImage);

        MemberSignupResponse response = memberMapper.toResponse(savedMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        MemberDetails member = authService.authenticate(request.getEmail(), request.getPassword());

        session.setAttribute(SessionConst.MEMBER_ID, member.getId());

        LoginResponse response = new LoginResponse("로그인에 성공했습니다.", member.getId(), member.getEmail());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response, HttpSession session) {
        session.invalidate();

        Cookie sessionCookie = new Cookie("JSESSIONID", null);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(0);
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);

        return ResponseEntity.noContent().build();
    }
}