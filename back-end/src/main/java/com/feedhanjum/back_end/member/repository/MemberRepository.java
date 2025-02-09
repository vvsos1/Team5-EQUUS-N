package com.feedhanjum.back_end.member.repository;

import com.feedhanjum.back_end.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m join fetch m.feedbackPreferences fp")
    Optional<Member> findMemberAndFeedbackPreferenceById(Long id);
}
