package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.LoadReceiverPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadSenderPort;
import com.feedhanjum.back_end.feedback.domain.Receiver;
import com.feedhanjum.back_end.feedback.domain.Sender;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LoadMemberAdapter implements LoadSenderPort, LoadReceiverPort {
    private final MemberRepository memberRepository;


    @Override
    public Sender loadSender(Long senderId) {
        Member member = memberRepository.findById(senderId).orElseThrow();
        return Sender.of(member);
    }

    @Override
    public Receiver loadReceiver(Long receiverId) {
        Member member = memberRepository.findById(receiverId).orElseThrow();
        return Receiver.of(member);
    }
}
