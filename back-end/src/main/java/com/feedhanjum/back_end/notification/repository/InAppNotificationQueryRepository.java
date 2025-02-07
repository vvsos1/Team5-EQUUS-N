package com.feedhanjum.back_end.notification.repository;

import com.feedhanjum.back_end.notification.domain.InAppNotification;
import com.feedhanjum.back_end.notification.domain.QInAppNotification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class InAppNotificationQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QInAppNotification notification = QInAppNotification.inAppNotification;

    public List<InAppNotification> getInAppNotifications(Long receiverId) {
        return queryFactory.
                select(notification)
                .from(notification)
                .where(notification.receiverId.eq(receiverId))
                .orderBy(notification.id.desc())
                .fetch();
    }
}
