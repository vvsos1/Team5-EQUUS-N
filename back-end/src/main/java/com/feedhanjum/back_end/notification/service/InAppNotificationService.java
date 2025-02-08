package com.feedhanjum.back_end.notification.service;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.notification.controller.dto.notification.InAppNotificationDto;
import com.feedhanjum.back_end.notification.domain.InAppNotification;
import com.feedhanjum.back_end.notification.repository.InAppNotificationQueryRepository;
import com.feedhanjum.back_end.notification.repository.InAppNotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InAppNotificationService {
    private final InAppNotificationRepository inAppNotificationRepository;
    private final InAppNotificationQueryRepository inAppNotificationQueryRepository;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public List<InAppNotificationDto> getInAppNotifications(Long receiverId) {
        return inAppNotificationQueryRepository.getInAppNotifications(receiverId).stream()
                .map(InAppNotificationDto::from).toList();
    }

    /**
     * @throw EntityNotFoundException receiverId에 해당하는 엔티티가 없을 때
     */
    @Transactional
    public void readInAppNotifications(Long receiverId, List<Long> notificationIds) {
        Member receiver = memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("없는 사용자"));
        List<InAppNotification> notifications = inAppNotificationRepository.findAllById(notificationIds);
        for (InAppNotification notification : notifications) {
            notification.read(receiver);
        }
    }

}
