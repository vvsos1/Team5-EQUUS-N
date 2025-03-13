package com.feedhanjum.feedback.application.service;

import com.feedhanjum.core.event.Events;
import com.feedhanjum.feedback.application.port.in.request.regular.CreateRegularFeedbackRequestsUseCase;
import com.feedhanjum.feedback.application.port.in.request.regular.command.CreateRegularFeedbackRequestsCommand;
import com.feedhanjum.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestListPort;
import com.feedhanjum.feedback.application.port.out.schedule.LoadParticipationPort;
import com.feedhanjum.feedback.application.port.out.schedule.LoadSchedulePort;
import com.feedhanjum.feedback.domain.AssociatedSchedule;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.schedule.event.RegularFeedbackRequestCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
class CreateRegularFeedbackRequestsService implements CreateRegularFeedbackRequestsUseCase {
    private final LoadParticipationPort loadParticipationPort;
    private final LoadMemberPort loadMemberPort;
    private final LoadSchedulePort loadSchedulePort;
    private final SaveRegularFeedbackRequestListPort saveRegularFeedbackRequestListPort;

    @Override
    @Transactional
    public void createRegularFeedbackRequests(CreateRegularFeedbackRequestsCommand command) {
        AssociatedSchedule schedule = loadSchedulePort.loadSchedule(command.getScheduleId()).orElseThrow();
        List<Long> memberIds = loadParticipationPort.loadParticipation(command.getScheduleId());

        List<FeedbackMember> members = loadMemberPort.loadMemberList(memberIds);

        List<RegularFeedbackRequest> requests = new ArrayList<>();
        LocalDateTime requestTime = schedule.getEndTime();
        for (FeedbackMember receiver : members) {
            for (FeedbackMember member : members) {
                if (Objects.equals(member.getId(), receiver.getId())) {
                    continue;
                }
                requests.add(new RegularFeedbackRequest(requestTime, member, schedule, receiver));
            }
            Events.raise(new RegularFeedbackRequestCreatedEvent(receiver.getId(), schedule.getId()));
        }
        saveRegularFeedbackRequestListPort.saveAll(requests);
    }
}
