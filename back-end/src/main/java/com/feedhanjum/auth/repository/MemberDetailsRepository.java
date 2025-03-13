package com.feedhanjum.auth.repository;

import com.feedhanjum.auth.domain.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Long> {
    Optional<MemberDetails> findByEmail(String email);
}
