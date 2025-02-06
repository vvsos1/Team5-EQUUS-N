package com.feedhanjum.back_end.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Entity
public class InAppNotification {
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column(nullable = false)
    protected Long receiverId;

    @Column(nullable = false)
    protected LocalDateTime createdAt;

    @Column(nullable = false)
    protected boolean isRead;

    @Column(name = "type", nullable = false, updatable = false, insertable = false)
    protected String type;

    protected InAppNotification(Long receiverId) {
        this.receiverId = receiverId;
    }
}
