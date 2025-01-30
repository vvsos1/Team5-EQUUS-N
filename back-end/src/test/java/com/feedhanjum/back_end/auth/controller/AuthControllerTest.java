package com.feedhanjum.back_end.auth.controller;

import com.feedhanjum.back_end.auth.controller.dto.MemberSignupRequest;
import com.feedhanjum.back_end.auth.controller.dto.MemberSignupResponse;
import com.feedhanjum.back_end.auth.controller.mapper.MemberMapper;
import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new com.feedhanjum.back_end.auth.exception.AuthExceptionHandler())
                .build();
    }

    @Nested
    @DisplayName("POST /api/auth/signup 테스트")
    class SignupTests {

        @Test
        @DisplayName("회원가입 성공 시 201(CREATED) 상태코드와 응답 반환")
        void signup_success() throws Exception {
            MemberSignupRequest request = MemberSignupRequest.builder()
                    .email("test@example.com")
                    .password("abcd1234")
                    .name("홍길동")
                    .build();

            MemberDetails entity = new MemberDetails(null, request.getEmail(), request.getPassword());
            when(memberMapper.toEntity(any(MemberSignupRequest.class))).thenReturn(entity);

            MemberDetails savedMember = new MemberDetails(1L, "test@example.com", "abcd1234");
            when(authService.registerMember(entity, request.getName())).thenReturn(savedMember);

            MemberSignupResponse response = new MemberSignupResponse(1L, "test@example.com", "회원가입이 완료되었습니다.");
            when(memberMapper.toResponse(savedMember)).thenReturn(response);

            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.email").value("test@example.com"))
                    .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));
        }

        @Test
        @DisplayName("이메일 중복 시 409(CONFLICT) 상태코드와 에러 메시지 반환")
        void signup_emailAlreadyExists() throws Exception {
            MemberSignupRequest request = MemberSignupRequest.builder()
                    .email("duplicate@example.com")
                    .password("abcd1234")
                    .name("홍길동")
                    .build();

            MemberDetails entity = new MemberDetails(null, request.getEmail(), request.getPassword());
            when(memberMapper.toEntity(any(MemberSignupRequest.class))).thenReturn(entity);

            doThrow(new EmailAlreadyExistsException("이미 사용 중인 이메일입니다."))
                    .when(authService).registerMember(entity, request.getName());

            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.error").value("EMAIL_ALREADY_EXISTS"))
                    .andExpect(jsonPath("$.message").value("이미 사용 중인 이메일입니다."));
        }

        @Test
        @DisplayName("유효하지 않은 입력값일 경우 400(BAD_REQUEST) 상태코드 반환")
        void signup_invalidInput() throws Exception {
            MemberSignupRequest request = MemberSignupRequest.builder()
                    .email("")
                    .password("12")
                    .name("")
                    .build();

            mockMvc.perform(post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }
}
