package com.feedhanjum.back_end.auth.service;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.exception.InvalidCredentialsException;
import com.feedhanjum.back_end.auth.passwordencoder.PasswordEncoder;
import com.feedhanjum.back_end.auth.repository.MemberDetailsRepository;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberDetailsRepository memberDetailsRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Nested
    @DisplayName("registerMember 테스트")
    class RegisterMemberTests {

        @Test
        @DisplayName("이메일 중복 없으면 정상 저장")
        void registerMember_success() {
            MemberDetails inputMember = MemberDetails.createEmailUser(null, "test@example.com", "pass1234");
            String name = "홍길동";
            List<FeedbackPreference> feedbackPreferences = List.of(FeedbackPreference.PROGRESSIVE, FeedbackPreference.COMPLEMENTING);

            when(memberDetailsRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.empty());

            Member savedMember = new Member(name, "test@example.com", null, feedbackPreferences);
            when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

            MemberDetails resultMemberDetails = MemberDetails.createEmailUser(1L, "test@example.com", "pass1234");
            when(memberDetailsRepository.save(any(MemberDetails.class))).thenReturn(resultMemberDetails);

            MemberDetails saved = authService.registerEmail(inputMember, name, null, feedbackPreferences);

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
            MemberDetails inputMember = MemberDetails.createEmailUser(null, "test@example.com", "pass1234");
            List<FeedbackPreference> feedbackPreferences = List.of(FeedbackPreference.PROGRESSIVE, FeedbackPreference.COMPLEMENTING);
            String name = "홍길동";

            when(memberDetailsRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(MemberDetails.createEmailUser(999L, "test@example.com", "any")));

            assertThatThrownBy(() -> authService.registerEmail(inputMember, name, null, feedbackPreferences))
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessage("이미 사용 중인 이메일입니다.");

            verify(memberRepository, never()).save(any());
            verify(memberDetailsRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("authenticate 테스트")
    class AuthenticateTests {

        @Test
        @DisplayName("인증 성공 시 MemberDetails 반환")
        void authenticate_success() {
            String email = "test@example.com";
            String password = "password";
            String hashedPassword = "password";

            MemberDetails member = MemberDetails.createEmailUser(1L, email, password);
            when(memberDetailsRepository.findByEmail(email)).thenReturn(Optional.of(member));

            when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

            MemberDetails result = authService.authenticateEmail(email, password);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getEmail()).isEqualTo(email);
            assertThat(result.getPassword()).isEqualTo(hashedPassword);

            verify(passwordEncoder).matches(password, hashedPassword);
        }

        @Test
        @DisplayName("이메일이 존재하지 않으면 InvalidCredentialsException 발생")
        void authenticate_fail_emailNotFound() {
            String email = "nonexistent@example.com";
            String password = "pass1234";

            when(memberDetailsRepository.findByEmail(email)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> authService.authenticateEmail(email, password))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("이메일 또는 비밀번호가 올바르지 않습니다.");

            verify(passwordEncoder, never()).matches(any(), any());
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면 InvalidCredentialsException 발생")
        void authenticate_fail_wrongPassword() {
            String email = "test@example.com";
            String password = "wrongpassword";
            String hashedPassword = "password"; // 예시 해시

            MemberDetails member = MemberDetails.createEmailUser(1L, email, hashedPassword);
            when(memberDetailsRepository.findByEmail(email)).thenReturn(Optional.of(member));

            when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

            assertThatThrownBy(() -> authService.authenticateEmail(email, password))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("이메일 또는 비밀번호가 올바르지 않습니다.");

            verify(passwordEncoder).matches(password, hashedPassword);
        }
    }
}
