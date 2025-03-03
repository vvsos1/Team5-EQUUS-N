package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@RequiredArgsConstructor
@Component
public class RegularFeedbackRequestMapper {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public RegularFeedbackRequestJpaEntity fromDomain(RegularFeedbackRequest domain) {
        return new RegularFeedbackRequestJpaEntity(
                domain.getCreatedAt(),
                domain.getRequester().getId(),
                domain.getSchedule().getId(),
                domain.getReceiver().getId()
        );
    }

    public RegularFeedbackRequest toDomain(RegularFeedbackRequestJpaEntity entity) {
        var requester = memberRepository.findById(entity.getRequesterId()).orElseThrow();
        var schedule = scheduleRepository.findById(entity.getScheduleId()).orElseThrow();
        var receiver = memberRepository.findById(entity.getReceiverId()).orElseThrow();
        var domain = new RegularFeedbackRequest(entity.getCreatedAt(), requester, schedule, receiver);
        setId(domain, entity.getId());
        return domain;
    }

    public void setId(RegularFeedbackRequest domain, Long id) {
        try {
            Field idField = domain.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set ID using reflection", e);
        }
    }
}
