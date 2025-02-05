package com.feedhanjum.back_end.schedule.repository;

import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.feedhanjum.back_end.member.domain.QMember.member;
import static com.feedhanjum.back_end.schedule.domain.QSchedule.schedule;
import static com.feedhanjum.back_end.schedule.domain.QScheduleMember.scheduleMember;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ScheduleMember> findScheduleTodoList(Long scheduleId, Long memberId) {
        return queryFactory.select(scheduleMember)
                .from(schedule)
                .join(schedule.scheduleMembers, scheduleMember).fetchJoin()
                .join(scheduleMember.todos).fetchJoin()
                .join(scheduleMember.member).fetchJoin()
                .where(schedule.id.eq(scheduleId), memberIdEq(memberId))
                .fetch();
    }

    public Schedule findScheduleByNearestTime(Long teamId, LocalDateTime time){
        return queryFactory.selectFrom(schedule)
                .where(schedule.team.id.eq(teamId), schedule.startTime.after(time))
                .orderBy(schedule.startTime.asc())
                .fetchFirst();
    }

    private BooleanExpression memberIdEq(Long memberId){
        return memberId == null ? null : member.id.eq(memberId);
    }
}
