package com.feedhanjum.feedback.adapter.out.persistence.request.regular;

import com.feedhanjum.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestListPort;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class RegularFeedbackRequestJdbcAdapter implements SaveRegularFeedbackRequestListPort {
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public void saveAll(List<RegularFeedbackRequest> requests) {
        jdbcAggregateTemplate.saveAll(requests);
    }
}
