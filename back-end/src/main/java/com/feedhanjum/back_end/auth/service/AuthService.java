package com.feedhanjum.back_end.auth.service;

import com.feedhanjum.back_end.auth.domain.MemberDetails;
import com.feedhanjum.back_end.auth.exception.EmailAlreadyExistsException;
import com.feedhanjum.back_end.auth.repository.MemberDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDetailsRepository memberDetailsRepository;

    @Transactional
    public MemberDetails registerMember(MemberDetails member) {
        memberDetailsRepository.findByEmail(member.getEmail())
                .ifPresent(existingMember -> {
                    throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
                });

        return memberDetailsRepository.save(member);
    }

}
