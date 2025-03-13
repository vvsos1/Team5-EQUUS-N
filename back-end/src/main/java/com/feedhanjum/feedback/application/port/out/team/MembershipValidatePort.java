package com.feedhanjum.feedback.application.port.out.team;

public interface MembershipValidatePort {

    boolean hasMembership(Long teamId, Long memberId);
}
