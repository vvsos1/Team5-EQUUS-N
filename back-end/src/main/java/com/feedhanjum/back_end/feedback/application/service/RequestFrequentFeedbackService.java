package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.RequestFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RequestFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.DeleteFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.LoadFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.SaveFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.LoadTeamPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.MembershipValidatePort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.exception.MembershipNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
class RequestFrequentFeedbackService implements RequestFrequentFeedbackUseCase {
    private final LoadTeamPort loadTeamPort;
    private final LoadMemberPort loadMemberPort;
    private final Clock clock;
    private final LoadFrequentFeedbackRequestPort loadFrequentFeedbackRequestPort;
    private final DeleteFrequentFeedbackRequestPort deleteFrequentFeedbackRequestPort;
    private final SaveFrequentFeedbackRequestPort saveFrequentFeedbackRequestPort;
    private final MembershipValidatePort membershipValidatePort;

    @Override
    @Retryable(retryFor = DuplicateKeyException.class)
    @Transactional
    public void requestFrequentFeedback(RequestFrequentFeedbackCommand command) {
        var teamId = command.getTeamId();
        var requesterId = command.getRequesterId();
        var receiverId = command.getReceiverId();

        validateMembership(teamId, requesterId);
        validateMembership(teamId, receiverId);

        AssociatedTeam team = loadTeamPort.loadTeam(teamId).orElseThrow();
        FeedbackMember requester = loadMemberPort.loadMember(requesterId).orElseThrow();
        FeedbackMember receiver = loadMemberPort.loadMember(receiverId).orElseThrow();
        LocalDateTime now = LocalDateTime.now(clock);

        // 기존 수시 피드백 요청 삭제
        loadFrequentFeedbackRequestPort.load(requesterId, teamId, receiverId)
                .ifPresent(exists -> deleteFrequentFeedbackRequestPort.deleteById(exists.getId()));

        FrequentFeedbackRequest request = new FrequentFeedbackRequest(command.getRequestedContent(), requester, team, receiver, now);

        saveFrequentFeedbackRequestPort.save(request);
    }

    private void validateMembership(long teamId, long memberId) {
        if (!membershipValidatePort.hasMembership(teamId, memberId)) {
            throw new MembershipNotFound(teamId, memberId);
        }
    }
}
