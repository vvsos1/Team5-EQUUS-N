package com.feedhanjum.back_end.auth.infra;

import com.feedhanjum.back_end.auth.exception.LoginStateRequiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new LoginStateRequiredException("로그인이 필요합니다.");
        }
        Object memberId = session.getAttribute(SessionConst.MEMBER_ID);
        if (memberId == null) {
            throw new LoginStateRequiredException("로그인이 필요합니다.");
        }

        return memberId;
    }
}
