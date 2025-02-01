package com.feedhanjum.back_end.member.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 아이디로 회원을 정상적으로 조회한다")
    void getMemberById_정상조회() {
        //given
        Long memberId = 1L;
        Member member = new Member("홍길동", "hong@example.com", "blue", "img.png");
        ReflectionTestUtils.setField(member, "id", memberId);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        Member result = memberService.getMemberById(memberId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(memberId);
        assertThat(result.getName()).isEqualTo("홍길동");
        assertThat(result.getEmail()).isEqualTo("hong@example.com");
        assertThat(result.getProfileBackgroundColor()).isEqualTo("blue");
        assertThat(result.getProfileImage()).isEqualTo("img.png");
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디 조회 시 예외를 발생시킨다")
    void getMemberById_회원없음() {
        //given
        Long memberId = 2L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> memberService.getMemberById(memberId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("찾으려는 해당 사용자가 없습니다.");
    }
}
