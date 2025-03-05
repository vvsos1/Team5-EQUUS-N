package com.feedhanjum.back_end.feedback.repository;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RegularFeedbackRequestRepository extends JpaRepository<RegularFeedbackRequest, Long> {

    void deleteAllByScheduleIdAndReceiverId(Long scheduleId, Long receiverId);

    @Modifying(clearAutomatically = true)
    @Query("delete from RegularFeedbackRequest rfr " +
            "where rfr.requester.id = :requesterId " +
            "and rfr.schedule.teamId = :teamId")
    void deleteAllByRequesterIdAndTeamId(Long requesterId, Long teamId);

    @Modifying(clearAutomatically = true)
    @Query("delete from RegularFeedbackRequest rfr " +
            "where rfr.receiver.id = :receiverId " +
            "and rfr.schedule.teamId = :teamId")
    void deleteAllByReceiverIdAndTeamId(Long receiverId, Long teamId);

}