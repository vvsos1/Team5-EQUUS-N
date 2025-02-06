package com.feedhanjum.back_end.notification.service;

import com.feedhanjum.back_end.notification.controller.dto.notification.InAppNotificationDto;
import com.feedhanjum.back_end.notification.repository.InAppNotificationQueryRepository;
import com.feedhanjum.back_end.notification.repository.InAppNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InAppNotificationService {
    private final InAppNotificationRepository inAppNotificationRepository;
    private final InAppNotificationQueryRepository inAppNotificationQueryRepository;


    public List<InAppNotificationDto> getInAppNotifications(Long receiverId) {
        return inAppNotificationQueryRepository.getInAppNotifications(receiverId).stream()
                .map(InAppNotificationDto::from).toList();
    }
}
