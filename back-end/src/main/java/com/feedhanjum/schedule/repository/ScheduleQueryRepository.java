package com.feedhanjum.schedule.repository;

import com.feedhanjum.schedule.repository.dto.ScheduleProjectionDto;
import com.feedhanjum.team.domain.QMembership;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.feedhanjum.member.domain.QMember.member;
import static com.feedhanjum.schedule.domain.QSchedule.schedule;
import static com.feedhanjum.schedule.domain.QScheduleMember.scheduleMember;
import static com.feedhanjum.schedule.domain.QTodo.todo;
import static com.feedhanjum.team.domain.QTeam.team;


@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QMembership membership = QMembership.membership;

    public List<ScheduleProjectionDto> findScheduleTodoList(Long scheduleId, Long memberId) {
        return queryScheduleProjectionDto()
                .where(schedule.id.eq(scheduleId), memberIdEq(memberId))
                .fetch();
    }

    public List<ScheduleProjectionDto> findScheduleByClosestNextStartTime(Long teamId, LocalDateTime time) {
        Long closestNextScheduleId = queryFactory.select(schedule.id)
                .from(schedule)
                .where(schedule.team.id.eq(teamId), schedule.startTime.after(time))
                .orderBy(schedule.startTime.asc())
                .fetchFirst();

        if (closestNextScheduleId == null) return List.of();

        return queryScheduleProjectionDto()
                .where(schedule.id.eq(closestNextScheduleId))
                .fetch();
    }

    public List<ScheduleProjectionDto> findScheduleByClosestPreviousEndTime(Long teamId, LocalDateTime time) {
        Long closestPreviousSchedule = queryFactory.select(schedule.id)
                .from(schedule)
                .where(schedule.team.id.eq(teamId), schedule.endTime.before(time))
                .orderBy(schedule.endTime.desc())
                .fetchFirst();

        if (closestPreviousSchedule == null) return List.of();

        return queryScheduleProjectionDto()
                .where(schedule.id.eq(closestPreviousSchedule))
                .fetch();
    }


    public List<ScheduleProjectionDto> findSchedulesByTeamIdAndDuration(Long memberId, Long teamId, LocalDateTime startTime, LocalDateTime endTime) {
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(team.id)
                .from(membership)
                .join(membership.team, team)
                .join(membership.member, member)
                .where(teamIdEq(teamId), memberIdEq(memberId));
        return queryScheduleProjectionDto()
                .where(schedule.team.id.in(subQuery), schedule.endTime.between(startTime, endTime))
                .fetch();
    }

    public Optional<LocalDateTime> findEarliestStartTimeByTeamId(Long teamId) {
        return Optional.ofNullable(queryFactory.select(schedule.startTime)
                .from(schedule)
                .where(teamIdEq(teamId))
                .orderBy(schedule.startTime.asc())
                .fetchFirst());
    }

    public Optional<LocalDateTime> findLatestEndTimeByTeamId(Long teamId) {
        return Optional.ofNullable(queryFactory.select(schedule.endTime)
                .from(schedule)
                .where(teamIdEq(teamId))
                .orderBy(schedule.endTime.desc())
                .fetchFirst());
    }


    private BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : member.id.eq(memberId);
    }

    private BooleanExpression teamIdEq(Long teamId) {
        return teamId == null ? null : team.id.eq(teamId);
    }

    private JPAQuery<ScheduleProjectionDto> queryScheduleProjectionDto() {
        return queryFactory.select(Projections.constructor(
                        ScheduleProjectionDto.class,
                        team.id,
                        team.name,
                        team.leader.id,
                        schedule.id,
                        schedule.name,
                        schedule.owner.id,
                        schedule.startTime,
                        schedule.endTime,
                        member.id,
                        member.name,
                        scheduleMember.id,
                        todo.content
                ))
                .from(scheduleMember)
                .join(scheduleMember.schedule, schedule)
                .join(schedule.team, team)
                .leftJoin(scheduleMember.todos, todo)
                .join(scheduleMember.member, member);
    }
}
