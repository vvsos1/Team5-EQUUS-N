package com.feedhanjum.back_end.member.repository;

import com.feedhanjum.back_end.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
