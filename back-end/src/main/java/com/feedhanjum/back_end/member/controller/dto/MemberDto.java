package com.feedhanjum.back_end.member.controller.dto;

import com.feedhanjum.back_end.member.domain.Member;

public record MemberDto(Long id, String name, String email, String backgroundColor, String backgroundImage) {
    public MemberDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getProfileBackgroundColor(), member.getProfileImage());
    }
}
