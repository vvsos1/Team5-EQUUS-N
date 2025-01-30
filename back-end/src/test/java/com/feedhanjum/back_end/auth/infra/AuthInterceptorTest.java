package com.feedhanjum.back_end.auth.infra;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthInterceptorTest {
    private AuthInterceptor authInterceptor;

    @BeforeEach
    void setUp() {
        authInterceptor = new AuthInterceptor();
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 보호된 엔드포인트에 접근 시 preHandle은 false 반환")
    void preHandle_unauthenticatedUser() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();

        when(request.getRequestURI()).thenReturn("/api/protected/resource");
        when(request.getSession(false)).thenReturn(null);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertFalse(result);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("인증된 사용자가 보호된 엔드포인트에 접근 시 preHandle은 true 반환")
    void preHandle_authenticatedUser() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();

        when(request.getRequestURI()).thenReturn("/api/protected/resource");
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));

        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionConst.MEMBER_ID)).thenReturn(new MemberDetails(1L, "test@example.com", "pass1234"));

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertTrue(result);
        verify(response, never()).setStatus(anyInt());
        verify(response, never()).setContentType(anyString());
        verify(response, never()).getWriter();
    }
}