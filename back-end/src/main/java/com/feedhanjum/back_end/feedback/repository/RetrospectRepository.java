package com.feedhanjum.back_end.feedback.repository;


import com.feedhanjum.back_end.feedback.domain.Retrospect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetrospectRepository extends JpaRepository<Retrospect, Long> {
}
