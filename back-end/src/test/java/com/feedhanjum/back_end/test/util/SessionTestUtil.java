package com.feedhanjum.back_end.test.util;

import com.feedhanjum.back_end.auth.infra.SessionConst;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.member.domain.Member;
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
