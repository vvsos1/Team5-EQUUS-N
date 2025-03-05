package com.feedhanjum.back_end.feedback.domain;


import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import jakarta.persistence.Embeddable;
import lombok.*;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FeedbackMember {

    private Long id;

    private String name;

    private String email;

    private ProfileImage profileImage;


    public static FeedbackMember of(Member member) {
        return new FeedbackMember(member.getId(), member.getName(), member.getEmail(), member.getProfileImage());
    }
}
