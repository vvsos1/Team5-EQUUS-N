package com.feedhanjum.back_end.schedule.domain;

import com.feedhanjum.back_end.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ScheduleMember {
    @Id
    @Column(name = "schedule_member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "scheduleMember")
    private final List<RegularFeedbackRequest> regularFeedbackRequests = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "todos",
            joinColumns = @JoinColumn(name = "schedule_member_id")
    )
    @Setter
    private List<Todo> todos;

    public ScheduleMember(Schedule schedule, Member member) {
        this.member = member;
        setSchedule(schedule);
    }


    private void setSchedule(Schedule schedule) {
        if (this.schedule != null) {
            this.schedule.getScheduleMembers().remove(this);
        }
        this.schedule = schedule;
        if (schedule != null && !schedule.getScheduleMembers().contains(this)) {
            schedule.getScheduleMembers().add(this);
        }
    }

}
