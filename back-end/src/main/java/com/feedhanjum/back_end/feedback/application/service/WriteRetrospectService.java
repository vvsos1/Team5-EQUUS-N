package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.retrospect.WriteRetrospectUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.retrospect.command.WriteRetrospectCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.retrospect.SaveRetrospectPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.LoadTeamPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.MembershipValidatePort;
import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;
import com.feedhanjum.back_end.feedback.domain.retrospect.RetrospectIdGenerator;
import com.feedhanjum.back_end.feedback.exception.MembershipNotFound;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
class WriteRetrospectService implements WriteRetrospectUseCase {
    private final RetrospectIdGenerator retrospectIdGenerator;
    private final Clock clock;
    private final LoadMemberPort loadMemberPort;
    private final LoadTeamPort loadTeamPort;
    private final MembershipValidatePort membershipValidatePort;
    private final SaveRetrospectPort saveRetrospectPort;

    public WriteRetrospectService(RetrospectIdGenerator retrospectIdGenerator, Clock clock, LoadMemberPort loadMemberPort, LoadTeamPort loadTeamPort, MembershipValidatePort membershipValidatePort, SaveRetrospectPort saveRetrospectPort) {
        this.retrospectIdGenerator = retrospectIdGenerator;
        this.clock = clock;
        this.loadMemberPort = loadMemberPort;
        this.loadTeamPort = loadTeamPort;
        this.membershipValidatePort = membershipValidatePort;
        this.saveRetrospectPort = saveRetrospectPort;
    }

    @Override
    public void writeRetrospect(WriteRetrospectCommand command) {
        validateMembership(command.getTeamId(), command.getWriterId());

        var writer = loadMemberPort.loadMember(command.getWriterId()).orElseThrow();
        var team = loadTeamPort.loadTeam(command.getTeamId()).orElseThrow();

        var now = LocalDateTime.now(clock);
        var id = retrospectIdGenerator.generateRetrospectId();

        var retrospect = new Retrospect(
                id,
                command.getTitle(),
                command.getContent(),
                writer,
                team,
                now);
        saveRetrospectPort.save(retrospect);
    }

    private void validateMembership(Long teamId, Long memberId) {
        if (!membershipValidatePort.hasMembership(teamId, memberId))
            throw new MembershipNotFound(teamId, memberId);
    }
}
