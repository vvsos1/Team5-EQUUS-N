package com.feedhanjum.back_end.feedback.repository;

import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId> {
}
