package com.feedhanjum.feedback.adapter.out.persistence.request.regular;

import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.test.annotation.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;

import static com.feedhanjum.feedback.test.FeedbackFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@AutoConfigureJdbc
@Import({RegularFeedbackRequestJdbcAdapter.class, RegularFeedbackRequestMapper.class})
class RegularFeedbackRequestJdbcAdapterTest {
    @Autowired
    RegularFeedbackRequestJdbcAdapter adapter;

    @Autowired
    RegularFeedbackRequestJpaEntityRepository repository;

    @Test
    @DisplayName("정기 피드백 요청 batch insert 테스트")
    void test1() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        var requests = new ArrayList<RegularFeedbackRequest>();
        for (int i = 0; i < 50; i++) {
            requests.add(createRegularFeedbackRequest(sender, schedule, receiver));
        }

        // when
        adapter.saveAll(requests);

        // then
        var all = repository.findAll();
        assertThat(all).hasSize(50);
    }
}