package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.CreateRegularFeedbackRequestsUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.CreateRegularFeedbackRequestsCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadParticipationPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.schedule.event.RegularFeedbackRequestCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CreateRegularFeedbackRequestsService implements CreateRegularFeedbackRequestsUseCase {
    private final LoadParticipationPort loadParticipationPort;
    private final LoadMemberPort loadMemberPort;
    private final LoadSchedulePort loadSchedulePort;
    private final SaveRegularFeedbackRequestPort saveRegularFeedbackRequestPort;

    @Transactional
    @Override
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
        // batch insert를 사용하도록 설정 필요
        saveRegularFeedbackRequestPort.saveRegularFeedbackRequests(requests);
    }
}
