package com.feedhanjum.back_end.feedback.domain;

import com.feedhanjum.back_end.schedule.domain.Schedule;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AssociatedSchedule {
    private Long id;

    private String name;

    private LocalDateTime endTime;

    private Long teamId;

    public static AssociatedSchedule of(Schedule schedule) {
        return new AssociatedSchedule(schedule.getId(), schedule.getName(), schedule.getEndTime(), schedule.getTeam().getId());
    }
}
