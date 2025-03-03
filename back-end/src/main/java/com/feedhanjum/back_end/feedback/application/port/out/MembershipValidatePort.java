package com.feedhanjum.back_end.feedback.application.port.out;

public interface MembershipValidatePort {

    boolean hasMembership(Long teamId, Long memberId);
}
