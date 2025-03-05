package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.member.repository.MemberRepository;
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
