package com.feedhanjum.back_end.auth.service;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.repository.MemberDetailsRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class AuthServiceTest {

    @Mock
    private MemberDetailsRepository memberDetailsRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("registerMember 테스트")
    class RegisterMemberTests {

        @Test
        @DisplayName("이메일 중복 없으면 정상 저장")
        void registerMember_success() {
            MemberDetails inputMember = new MemberDetails(null, "test@example.com", "pass1234");
            String name = "홍길동";

            when(memberDetailsRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.empty());

            Member savedMember = new Member(name, "test@example.com");
            when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

            MemberDetails resultMemberDetails = new MemberDetails(1L, "test@example.com", "pass1234");
            when(memberDetailsRepository.save(any(MemberDetails.class))).thenReturn(resultMemberDetails);

            MemberDetails saved = authService.registerMember(inputMember, name);

            assertThat(saved).isNotNull();
            assertThat(saved.getId()).isEqualTo(1L);
            assertThat(saved.getEmail()).isEqualTo("test@example.com");
            assertThat(saved.getPassword()).isEqualTo("pass1234");

            ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
            verify(memberRepository).save(memberCaptor.capture());
            Member capturedMember = memberCaptor.getValue();
            assertThat(capturedMember.getName()).isEqualTo("홍길동");
            assertThat(capturedMember.getEmail()).isEqualTo("test@example.com");

            verify(memberDetailsRepository).save(any(MemberDetails.class));
        }

        @Test
        @DisplayName("이메일 중복 시 EmailAlreadyExistsException 발생")
        void registerMember_fail_emailAlreadyExists() {
            MemberDetails inputMember = new MemberDetails(null, "test@example.com", "pass1234");
            String name = "홍길동";

            when(memberDetailsRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(new MemberDetails(999L, "test@example.com", "any")));

            assertThatThrownBy(() -> authService.registerMember(inputMember, name))
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessage("이미 사용 중인 이메일입니다.");

            verify(memberRepository, never()).save(any());
            verify(memberDetailsRepository, never()).save(any());
        }
    }
}
