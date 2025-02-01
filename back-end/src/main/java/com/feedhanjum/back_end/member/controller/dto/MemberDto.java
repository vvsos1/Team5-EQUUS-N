package com.feedhanjum.back_end.member.controller.dto;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;

public record MemberDto(Long id, String name, String email, ProfileImage profileImage) {
    public MemberDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getProfileImage());
    }
}
