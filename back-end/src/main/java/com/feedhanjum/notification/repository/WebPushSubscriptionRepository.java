package com.feedhanjum.notification.repository;

import com.feedhanjum.member.domain.Member;
import com.feedhanjum.notification.domain.WebPushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WebPushSubscriptionRepository extends JpaRepository<WebPushSubscription, Long> {
    List<WebPushSubscription> findAllBySubscriber(Member subscriber);

    Optional<WebPushSubscription> findBySubscription_Endpoint(String subscriptionEndpoint);
}