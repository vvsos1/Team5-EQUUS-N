package com.feedhanjum.feedback.application.service;

import com.feedhanjum.core.event.EventPublisher;
import com.feedhanjum.core.event.Events;
import com.feedhanjum.feedback.application.port.in.request.regular.command.CreateRegularFeedbackRequestsCommand;
import com.feedhanjum.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestListPort;
import com.feedhanjum.feedback.application.port.out.schedule.LoadParticipationPort;
import com.feedhanjum.feedback.application.port.out.schedule.LoadSchedulePort;
import com.feedhanjum.feedback.domain.AssociatedSchedule;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.schedule.event.RegularFeedbackRequestCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.feedhanjum.feedback.test.FeedbackFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRegularFeedbackRequestsServiceTest {
    @Mock
    LoadParticipationPort loadParticipationPort;
    @Mock
    LoadMemberPort loadMemberPort;
    @Mock
    LoadSchedulePort loadSchedulePort;
    @Mock
    SaveRegularFeedbackRequestListPort saveRegularFeedbackRequestListPort;

    @InjectMocks
    CreateRegularFeedbackRequestsService createRegularFeedbackRequestsService;

    EventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        eventPublisher = mock();
        Events.setPublisher(eventPublisher);
    }


    private void givenMemberWillLoad(List<FeedbackMember> members) {
        var memberIds = members.stream().map(FeedbackMember::getId).toList();
        when(loadMemberPort.loadMemberList(memberIds)).thenReturn(members);
    }

    private void givenScheduleWillLoad(AssociatedSchedule schedule) {
        when(loadSchedulePort.loadSchedule(schedule.getId())).thenReturn(Optional.of(schedule));
    }

    private void givenScheduleWillNotLoad() {
        when(loadSchedulePort.loadSchedule(any())).thenReturn(Optional.empty());
    }

    private void givenParticipationWillLoad(Long scheduleId, List<Long> memberIds) {
        when(loadParticipationPort.loadParticipation(scheduleId)).thenReturn(memberIds);
    }

    @Test
    @DisplayName("정기 피드백 요청 생성 성공")
    void test1() {
        // given
        var member1 = createMember("member1");
        var member2 = createMember("member2");
        var member3 = createMember("member3");
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        var command = new CreateRegularFeedbackRequestsCommand(schedule.getId());

        var members = List.of(member1, member2, member3);
        var memberIds = members.stream().map(FeedbackMember::getId).toList();

        givenScheduleWillLoad(schedule);
        givenParticipationWillLoad(schedule.getId(), memberIds);
        givenMemberWillLoad(members);

        // when
        createRegularFeedbackRequestsService.createRegularFeedbackRequests(command);

        // then
        ArgumentCaptor<List<RegularFeedbackRequest>> requestsCaptor = ArgumentCaptor.captor();
        verify(saveRegularFeedbackRequestListPort).saveAll(requestsCaptor.capture());
        List<RegularFeedbackRequest> requests = requestsCaptor.getValue();
        assertThat(requests).hasSize(6);
        assertThat(requests)
                .filteredOn(r -> r.getReceiver() == member1)
                .extracting(RegularFeedbackRequest::getRequester)
                .containsExactlyInAnyOrder(member2, member3);
        assertThat(requests)
                .filteredOn(r -> r.getReceiver() == member2)
                .extracting(RegularFeedbackRequest::getRequester)
                .containsExactlyInAnyOrder(member1, member3);

        assertThat(requests)
                .filteredOn(r -> r.getReceiver() == member3)
                .extracting(RegularFeedbackRequest::getRequester)
                .containsExactlyInAnyOrder(member1, member2);

        ArgumentCaptor<RegularFeedbackRequestCreatedEvent> eventCaptor = ArgumentCaptor.captor();
        verify(eventPublisher, times(3)).publishEvent(eventCaptor.capture());
        List<RegularFeedbackRequestCreatedEvent> events = eventCaptor.getAllValues();
        assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::receiverId)
                .containsExactlyInAnyOrder(member1.getId(), member2.getId(), member3.getId());
        assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::scheduleId)
                .containsOnly(schedule.getId());
    }

    @Test
    @DisplayName("정기 피드백 요청 생성 실패 - schedule이 없을 경우")
    void test2() {
        // given
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        var command = new CreateRegularFeedbackRequestsCommand(schedule.getId());

        givenScheduleWillNotLoad();

        // when & then
        assertThatThrownBy(() -> createRegularFeedbackRequestsService.createRegularFeedbackRequests(command));
    }

}