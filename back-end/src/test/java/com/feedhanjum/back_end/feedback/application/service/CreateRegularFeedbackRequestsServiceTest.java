package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadParticipationPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRegularFeedbackRequestsServiceTest {
    @Mock
    LoadParticipationPort loadParticipationPort;
    @Mock
    LoadMemberPort loadMemberPort;
    @Mock
    LoadSchedulePort loadSchedulePort;
    @Mock
    SaveRegularFeedbackRequestPort saveRegularFeedbackRequestPort;

    @InjectMocks
    CreateRegularFeedbackRequestsService createRegularFeedbackRequestsService;

//    @Test
//    @DisplayName("정기 피드백 요청 생성 성공")
//    void test1() {
//        // given
//        Member member1 = createMember("member1");
//        Member member2 = createMember("member2");
//        Member member3 = createMember("member3");
//        Team team = createTeam("team", member1);
//        Schedule schedule = createSchedule("schedule", team, member1, true);
//        ScheduleMember scheduleMember1 = new ScheduleMember(schedule, member1);
//        ScheduleMember scheduleMember2 = new ScheduleMember(schedule, member2);
//        ScheduleMember scheduleMember3 = new ScheduleMember(schedule, member3);
//
//        when(scheduleRepository.findByIdWithMembers(schedule.getId())).thenReturn(Optional.of(schedule));
//        // when
//        feedbackService.createRegularFeedbackRequests(schedule.getId());
//
//        // then
//        ArgumentCaptor<List<RegularFeedbackRequest>> requestsCaptor = ArgumentCaptor.captor();
//        verify(regularFeedbackRequestRepository).saveAll(requestsCaptor.capture());
//        List<RegularFeedbackRequest> requests = requestsCaptor.getValue();
//        assertThat(requests).hasSize(6);
//        assertThat(requests)
//                .filteredOn(r -> r.getReceiver() == member1)
//                .extracting(RegularFeedbackRequest::getRequester)
//                .containsExactlyInAnyOrder(member2, member3);
//        assertThat(requests)
//                .filteredOn(r -> r.getReceiver() == member2)
//                .extracting(RegularFeedbackRequest::getRequester)
//                .containsExactlyInAnyOrder(member1, member3);
//
//        assertThat(requests)
//                .filteredOn(r -> r.getReceiver() == member3)
//                .extracting(RegularFeedbackRequest::getRequester)
//                .containsExactlyInAnyOrder(member1, member2);
//
//        ArgumentCaptor<RegularFeedbackRequestCreatedEvent> eventCaptor = ArgumentCaptor.captor();
//        verify(eventPublisher, times(3)).publishEvent(eventCaptor.capture());
//        List<RegularFeedbackRequestCreatedEvent> events = eventCaptor.getAllValues();
//        assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::receiverId)
//                .containsExactlyInAnyOrder(member1.getId(), member2.getId(), member3.getId());
//        assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::scheduleId)
//                .containsOnly(schedule.getId());
//    }
//
//    @Test
//    @DisplayName("정기 피드백 요청 생성 실패 - schedule이 없을 경우")
//    void test2() {
//        // given
//        Long scheduleId = 1L;
//
//        when(scheduleRepository.findByIdWithMembers(scheduleId)).thenReturn(Optional.empty());
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService.createRegularFeedbackRequests(scheduleId))
//                .isInstanceOf(EntityNotFoundException.class);
//
//        verify(regularFeedbackRequestRepository, never()).saveAll(any());
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackRequestCreatedEvent.class));
//    }

}