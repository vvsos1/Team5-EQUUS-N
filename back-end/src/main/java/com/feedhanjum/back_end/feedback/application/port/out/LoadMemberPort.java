package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.FeedbackMember;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LoadMemberPort {
    default Optional<FeedbackMember> loadMember(Long memberId) {
        List<FeedbackMember> members = loadMemberList(List.of(memberId));
        if (members.isEmpty())
            return Optional.empty();
        return Optional.of(members.get(0));
    }

    List<FeedbackMember> loadMemberList(Collection<Long> memberIds);
}
