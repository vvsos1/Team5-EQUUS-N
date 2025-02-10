package com.feedhanjum.back_end.notification.domain;

import com.feedhanjum.back_end.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
public class WebPushSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private Member subscriber;

    @Column(name = "subscription", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Subscription subscription;

    public WebPushSubscription(Member subscriber, Subscription subscription) {
        this.subscriber = subscriber;
        this.subscription = subscription;
    }
}
