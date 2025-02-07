package com.feedhanjum.back_end.auth.controller;

import com.feedhanjum.back_end.auth.controller.dto.*;
import com.feedhanjum.back_end.auth.controller.mapper.MemberMapper;
import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.domain.PasswordResetToken;
import com.feedhanjum.back_end.auth.domain.SignupToken;
import com.feedhanjum.back_end.auth.exception.PasswordResetTokenNotValidException;
import com.feedhanjum.back_end.auth.exception.PasswordResetTokenVerifyRequiredException;
import com.feedhanjum.back_end.auth.exception.SignupTokenNotValidException;
import com.feedhanjum.back_end.auth.exception.SignupTokenVerifyRequiredException;
import com.feedhanjum.back_end.auth.infra.SessionConst;
import com.feedhanjum.back_end.auth.service.AuthService;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberMapper memberMapper;

    /**
     * 회원가입을 처리하는 핸들러
     *
     * @param request
     * @return 회원 가입 성공 여부
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponse> signup(
            HttpSession session,
            @Valid @RequestBody MemberSignupRequest request) {
        Object emailObject = session.getAttribute(SessionConst.SIGNUP_TOKEN_VERIFIED_EMAIL);
        if (!(emailObject instanceof String email) || !email.equals(request.email())) {
            throw new SignupTokenVerifyRequiredException();
        }

        MemberDetails member = memberMapper.toEntity(request);
        String name = request.name();
        ProfileImage profileImage = request.profileImage();

        MemberDetails savedMember = authService.registerMember(member, name, profileImage);

        session.removeAttribute(SessionConst.SIGNUP_TOKEN_VERIFIED_EMAIL);
        MemberSignupResponse response = memberMapper.toResponse(savedMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        MemberDetails member = authService.authenticate(request.email(), request.password());

        session.setAttribute(SessionConst.MEMBER_ID, member.getId());
        session.setMaxInactiveInterval(Integer.MAX_VALUE);

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

    @Operation(summary = "회원가입 이메일 중복검증 & 토큰 발송")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 검증 토큰 발송 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "409", description = "이메일 중복", content = @Content),
            @ApiResponse(responseCode = "429", description = "이메일 발송 지연으로 인해 실패", content = @Content)
    })
    @PostMapping(value = "/send-signup-verification-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupEmailSendResponse> sendSignupVerificationEmail(HttpSession session, @Valid @RequestBody SignupEmailSendRequest request) {
        SignupToken signupToken = authService.sendSignupVerificationEmail(request.email());
        session.setAttribute(SessionConst.SIGNUP_TOKEN, signupToken);
        SignupEmailSendResponse signupEmailSendResponse = new SignupEmailSendResponse(signupToken.getExpireDate());
        return ResponseEntity.ok(signupEmailSendResponse);
    }

    @Operation(summary = "회원가입 이메일 토큰 인증")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "이메일 토큰 인증 성공. 세션에 해당 내역 저장"),
            @ApiResponse(responseCode = "400", description = "이메일 토큰 인증 실패")
    })
    @PostMapping("/verify-signup-email-token")
    public ResponseEntity<Void> verifySignupEmailToken(HttpSession session, @Valid @RequestBody SignupEmailVerifyRequest request) {
        Object token = session.getAttribute(SessionConst.SIGNUP_TOKEN);
        if (!(token instanceof SignupToken signupToken)) {
            throw new SignupTokenNotValidException();
        }
        authService.validateSignupToken(signupToken, request.email(), request.code());
        session.setAttribute(SessionConst.SIGNUP_TOKEN_VERIFIED_EMAIL, signupToken.getEmail());
        session.removeAttribute(SessionConst.SIGNUP_TOKEN);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "비밀번호 초기화 이메일 토큰 발송")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 초기화 토큰 발송 성공. 이메일이 존재하지 않아도 보안상 발송 성공처리"),
            @ApiResponse(responseCode = "429", description = "이메일 발송 지연으로 인해 실패", content = @Content)

    })
    @PostMapping(value = "/send-password-reset-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PasswordResetEmailSendResponse> sendPasswordResetEmail(
            HttpSession session,
            @Valid @RequestBody PasswordResetEmailSendRequest request) {
        Optional<PasswordResetToken> passwordResetTokenOptional = authService.sendPasswordResetEmail(request.email());
        PasswordResetEmailSendResponse passwordResetEmailSendResponse;
        if (passwordResetTokenOptional.isEmpty()) {
            passwordResetEmailSendResponse = new PasswordResetEmailSendResponse(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRE_MINUTE));
        } else {
            PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
            session.setAttribute(SessionConst.PASSWORD_RESET_TOKEN, passwordResetToken);
            passwordResetEmailSendResponse = new PasswordResetEmailSendResponse(passwordResetToken.getExpireDate());
        }
        return ResponseEntity.ok(passwordResetEmailSendResponse);
    }

    @Operation(summary = "비밀번호 초기화 이메일 토큰 인증")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "비밀번호 초기화 토큰 인증 성공. 세션에 해당 내역 저장"),
            @ApiResponse(responseCode = "400", description = "비밀번호 초기화 토큰 인증 실패")
    })
    @PostMapping("/verify-password-reset-token")
    public ResponseEntity<Void> verifyPasswordResetToken(HttpSession session, @Valid @RequestBody PasswordResetEmailVerifyRequest request) {
        Object token = session.getAttribute(SessionConst.PASSWORD_RESET_TOKEN);
        if (!(token instanceof PasswordResetToken passwordResetToken)) {
            throw new PasswordResetTokenNotValidException();
        }
        authService.validatePasswordResetToken(passwordResetToken, request.email(), request.code());
        session.setAttribute(SessionConst.PASSWORD_RESET_TOKEN_VERIFIED_EMAIL, passwordResetToken.getEmail());
        session.removeAttribute(SessionConst.PASSWORD_RESET_TOKEN);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "비밀번호 초기화", description = "비밀번호를 초기화한다. 미리 비밀번호 초기화 이메일 토큰 인증이 완료되었어야 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "비밀번호 초기화 성공"),
            @ApiResponse(responseCode = "401", description = "비밀번호 검증 토큰 인증 필요")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(HttpSession session,
                                              @Valid @RequestBody PasswordResetRequest request
    ) {
        Object emailObject = session.getAttribute(SessionConst.PASSWORD_RESET_TOKEN_VERIFIED_EMAIL);
        if (!(emailObject instanceof String email) || !email.equals(request.email())) {
            throw new PasswordResetTokenVerifyRequiredException();
        }
        authService.resetPassword(request.email(), request.newPassword());
        session.removeAttribute(SessionConst.PASSWORD_RESET_TOKEN_VERIFIED_EMAIL);
        return ResponseEntity.noContent().build();
    }

}