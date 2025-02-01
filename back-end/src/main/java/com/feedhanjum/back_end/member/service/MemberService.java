package com.feedhanjum.back_end.member.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(Long findMemberId) {
        Member findMember = memberRepository.findById(findMemberId).orElseThrow(() -> new EntityNotFoundException("찾으려는 해당 사용자가 없습니다."));
        // 나중에 soft delete 구현 시, 탈퇴한 회원 여부를 검사해야 함.
        return findMember;
    }
}
