package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Component
class LoadMemberAdapter implements LoadMemberPort {
    private final MemberRepository memberRepository;

    @Override
    public List<FeedbackMember> loadMemberList(Collection<Long> memberIds) {
        return memberRepository.findAllById(memberIds).stream().map(FeedbackMember::of).toList();
    }
}
