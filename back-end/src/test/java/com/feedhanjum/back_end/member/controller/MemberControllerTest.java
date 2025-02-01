package com.feedhanjum.back_end.member.controller;

import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Test
    @DisplayName("회원 아이디로 회원 정보를 정상적으로 조회한다")
    void getMemberById_정상조회() {
        //given
        Long memberId = 1L;
        Member member = new Member("홍길동", "hong@example.com", "blue", "img.png");
        ReflectionTestUtils.setField(member, "id", memberId);
        when(memberService.getMemberById(memberId)).thenReturn(member);

        //when
        MemberDto result = memberController.getMemberById(memberId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(memberId);
        assertThat(result.name()).isEqualTo("홍길동");
        assertThat(result.email()).isEqualTo("hong@example.com");
        assertThat(result.backgroundColor()).isEqualTo("blue");
        assertThat(result.backgroundImage()).isEqualTo("img.png");
    }
}
