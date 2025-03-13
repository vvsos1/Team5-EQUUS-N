package com.feedhanjum.test.util;

import com.feedhanjum.auth.infra.SessionConst;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.member.domain.Member;
import org.springframework.mock.web.MockHttpSession;

public class SessionTestUtil {

    public static MockHttpSession withLoginUser(Member member) {
        return withLoginUserId(member.getId());
    }

    public static MockHttpSession withLoginUser(FeedbackMember member) {
        return withLoginUserId(member.getId());
    }

    public static MockHttpSession withLoginUserId(Long memberId) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_ID, memberId);
        return session;
    }
}
