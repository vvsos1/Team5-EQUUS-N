package com.feedhanjum.back_end.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.notification.config.WebPushProperty;
import com.feedhanjum.back_end.notification.domain.WebPushSubscription;
import com.feedhanjum.back_end.notification.repository.WebPushSubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.PushAsyncService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebPushService {
    private final WebPushSubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final PushAsyncService pushService;
    private final WebPushProperty webPushProperty;
    private final ObjectMapper mapper;

    /**
     * @throws EntityNotFoundException 해당 회원을 찾을 수 없을 때
     */
    @Transactional
    public void subscribe(Long subscriberId, Subscription subscription) {
        Member member = memberRepository.findById(subscriberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
        subscriptionRepository.save(new WebPushSubscription(member, subscription));
        log.info("Web push subscription saved: {} {}", member.getName(), subscription);
    }


    public String getApplicationServerKey() {
        return webPushProperty.getVapid().getPublicKey();
    }

}
