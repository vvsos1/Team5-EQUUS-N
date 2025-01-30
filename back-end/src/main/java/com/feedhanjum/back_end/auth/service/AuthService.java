package com.feedhanjum.back_end.auth.service;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.passwordencoder.PasswordEncoder;
import com.feedhanjum.back_end.auth.repository.MemberDetailsRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDetailsRepository memberDetailsRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * 회원가입을 처리하는 서비스
     * Member 테이블에서 ID값을 받아와 저장하고, 암호를 해싱한 뒤 반환한다.
     * @param memberDetails 사용자의 인증을 담당하는 정보
     * @param name 사용자가 설정한 활동 이름
     * @throws EmailAlreadyExistsException 이미 이메일이 존재하는 경우 
     * @return id값이 할당된 인증 정보 반환
     */
    @Transactional
    public MemberDetails registerMember(MemberDetails memberDetails, String name) {
        memberDetailsRepository.findByEmail(memberDetails.getEmail())
                .ifPresent(existingMember -> {
                    throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
                });

        MemberDetails savedMemberDetails = getSavedMemberDetails(memberDetails, name);

        return memberDetailsRepository.save(savedMemberDetails);
    }

    private MemberDetails getSavedMemberDetails(MemberDetails memberDetails, String name) {
        Member member = new Member(name, memberDetails.getEmail());
        Member savedMember = memberRepository.save(member);
        String hashedPassword = passwordEncoder.encode(memberDetails.getPassword());
        return new MemberDetails(savedMember.getId(), memberDetails.getEmail(), hashedPassword);
    }

}
