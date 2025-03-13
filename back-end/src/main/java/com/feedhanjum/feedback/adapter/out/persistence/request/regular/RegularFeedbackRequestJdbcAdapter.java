package com.feedhanjum.feedback.adapter.out.persistence.request.regular;

import com.feedhanjum.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestListPort;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class RegularFeedbackRequestJdbcAdapter implements SaveRegularFeedbackRequestListPort {
    private static final String INSERT_SQL = "INSERT INTO regular_feedback_request " +
            "(requester_id, requester_name, requester_email,requester_background_color," +
            "requester_image,schedule_id,schedule_name,schedule_end_time,team_id," +
            "receiver_id,receiver_name,receiver_email,receiver_background_color,receiver_image," +
            "created_at) VALUES " +
            "(:requester.id,:requester.name,:requester.email,:requester.profileImage.backgroundColor,:requester.profileImage.image," +
            ":schedule.id,:schedule.name,:schedule.endTime,:schedule.teamId," +
            ":receiver.id,:receiver.name,:receiver.email,:receiver.profileImage.backgroundColor,:receiver.profileImage.image," +
            ":createdAt)";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RegularFeedbackRequestMapper regularFeedbackRequestMapper;

    @Override
    public void saveAll(List<RegularFeedbackRequest> requests) {

        var entities = requests.stream().map(regularFeedbackRequestMapper::fromDomain).toList();
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(entities);
        namedParameterJdbcTemplate.batchUpdate(INSERT_SQL, params);
    }
}
