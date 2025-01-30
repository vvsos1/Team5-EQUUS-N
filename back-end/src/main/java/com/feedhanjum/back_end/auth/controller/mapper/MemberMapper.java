package com.feedhanjum.back_end.auth.controller.mapper;

import com.feedhanjum.back_end.auth.controller.dto.MemberSignupRequest;
import com.feedhanjum.back_end.auth.controller.dto.MemberSignupResponse;
import com.feedhanjum.back_end.auth.domain.MemberDetails;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDetails toEntity(MemberSignupRequest request) {
        return new MemberDetails(request.getEmail(), request.getPassword());
    }

    public MemberSignupResponse toResponse(MemberDetails member) {
        return new MemberSignupResponse(member.getId(), member.getEmail(), "회원가입이 완료되었습니다.");
    }
}