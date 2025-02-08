package com.feedhanjum.back_end.auth.service;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.domain.PasswordResetToken;
import com.feedhanjum.back_end.auth.domain.SignupToken;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.exception.InvalidCredentialsException;
import com.feedhanjum.back_end.auth.exception.PasswordResetTokenNotValidException;
import com.feedhanjum.back_end.auth.exception.SignupTokenNotValidException;
import com.feedhanjum.back_end.auth.passwordencoder.PasswordEncoder;
import com.feedhanjum.back_end.auth.repository.MemberDetailsRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDetailsRepository memberDetailsRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    /**
     * 회원가입을 처리하는 서비스
     * Member 테이블에서 ID값을 받아와 저장하고, 암호를 해싱한 뒤 반환한다.
     *
     * @param memberDetails 사용자의 인증을 담당하는 정보
     * @param name          사용자가 설정한 활동 이름
     * @return id값이 할당된 인증 정보 반환
     * @throws EmailAlreadyExistsException 이미 이메일이 존재하는 경우
     */
    @Transactional
    public MemberDetails registerMember(MemberDetails memberDetails, String name, ProfileImage profileImage) {

        validateEmail(memberDetails.getEmail());
        Member member = new Member(name, memberDetails.getEmail(), profileImage);
        Member savedMember = memberRepository.save(member);
        String hashedPassword = passwordEncoder.encode(memberDetails.getPassword());
        MemberDetails savedMemberDetails = new MemberDetails(savedMember.getId(), memberDetails.getEmail(), hashedPassword);

        return memberDetailsRepository.save(savedMemberDetails);
    }

    /**
     * 로그인을 처리하기 위해, 인증 로직을 담당하는 서비스
     *
     * @param email
     * @param password
     * @return
     * @throws InvalidCredentialsException 이메일 혹은 비밀번호가 올바르지 않은 경우
     */
    public MemberDetails authenticate(String email, String password) {
        MemberDetails member = memberDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return member;
    }

    /**
     * @throws EmailAlreadyExistsException 이미 이메일이 존재하는 경우
     */
    public SignupToken sendSignupVerificationEmail(String email) {
        SignupToken token = SignupToken.generateNewToken(email);
        validateEmail(email);
        emailService.sendMail(
                email,
                "피드한줌 회원가입 이메일 인증",
                "회원가입을 위한 이메일입니다. 아래의 코드를 회원가입 창에 입력해주세요 " +
                token.getCode() +
                " 유효기간은 " + SignupToken.EXPIRE_MINUTE + "분입니다"
        );
        return token;
    }

    /**
     * @throws SignupTokenNotValidException 토큰 검증 실패
     */
    public void validateSignupToken(SignupToken existToken, String email, String token) {
        existToken.validateToken(email, token);
    }


    /**
     * @return 이메일 발송시도를 성공한 경우 발송한 토큰 정보. 회원가입된 이메일이 아니라면 Optional.empty()
     */
    public Optional<PasswordResetToken> sendPasswordResetEmail(String email) {
        Optional<MemberDetails> memberDetails = memberDetailsRepository.findByEmail(email);
        if (memberDetails.isEmpty()) {
            return Optional.empty();
        }

        PasswordResetToken token = PasswordResetToken.generateNewToken(email);
        emailService.sendMail(
                email,
                "피드한줌 비밀번호 초기화 이메일",
                "비밀번호 초기화를 위한 이메일입니다. 아래의 코드를 비밀번호 초기화 창에 입력해주세요 " +
                token.getCode() +
                " 유효기간은 " + PasswordResetToken.EXPIRE_MINUTE + "분입니다"
        );
        return Optional.of(token);
    }


    /**
     * @throws PasswordResetTokenNotValidException 토큰 검증 실패
     */
    public void validatePasswordResetToken(PasswordResetToken existToken, String email, String token) {
        existToken.validateToken(email, token);
    }


    /**
     * @throws RuntimeException 이메일로 가입된 사용자가 없을 경우. 앞선 검증 로직 상 발생할 수 없음
     */
    @Transactional
    public void resetPassword(String email, String newPassword) {
        MemberDetails memberDetails = memberDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("가입되지 않은 사용자입니다"));

        String newHashedPassword = passwordEncoder.encode(newPassword);
        memberDetails.changePassword(newHashedPassword);
    }


    private void validateEmail(String email) {
        memberDetailsRepository.findByEmail(email)
                .ifPresent(existingMember -> {
                    throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
                });
    }

}
