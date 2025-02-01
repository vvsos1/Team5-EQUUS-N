package com.feedhanjum.back_end.schedule.repository;

import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {

    Optional<ScheduleMember> findByMemberIdAndScheduleId(Long memberId, Long scheduleId);
}