package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.command.SkipRegularFeedbackRequestCommand;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestListPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.test.FeedbackFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.feedhanjum.back_end.feedback.test.FeedbackFixture.createMember;
import static com.feedhanjum.back_end.feedback.test.FeedbackFixture.createRegularFeedbackRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkipRegularFeedbackRequestServiceTest {
    @Mock
    LoadRegularFeedbackRequestListPort loadRegularFeedbackRequestListPort;
    @Mock
    DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @InjectMocks
    SkipRegularFeedbackRequestService skipRegularFeedbackRequestService;

    @Test
    @DisplayName("정기 피드백 건너뛰기 성공")
    void test1() {
        // given
        var team = FeedbackFixture.defaultTeam();
        var schedule = FeedbackFixture.defaultSchedule(team.getId());
        var receiver = FeedbackFixture.defaultReceiver();

        var requests = new ArrayList<RegularFeedbackRequest>();

        for (int i = 0; i < 5; i++) {
            requests.add(createRegularFeedbackRequest(createMember("requester" + i), schedule, receiver));
        }

        var command = new SkipRegularFeedbackRequestCommand(schedule.getId(), receiver.getId());

        givenRegularFeedbackRequestsWillLoad(requests);

        // when
        skipRegularFeedbackRequestService.skipRegularFeedbackRequest(command);

        // then
        verify(deleteRegularFeedbackRequestPort)
                .deleteRegularFeedbackRequests(requests.stream().map(RegularFeedbackRequest::getId).toList());
    }

    private void givenRegularFeedbackRequestsWillLoad(List<RegularFeedbackRequest> requests) {
        RegularFeedbackRequest request = requests.get(0);
        when(loadRegularFeedbackRequestListPort.loadRegularFeedbackRequestList(request.getSchedule().getId(), request.getReceiver().getId()))
                .thenReturn(requests);
    }
}