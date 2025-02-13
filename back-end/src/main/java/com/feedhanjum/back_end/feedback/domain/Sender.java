package com.feedhanjum.back_end.feedback.domain;


import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Sender {
    private Long id;

    private String name;

    @Embedded
    private ProfileImage profileImage;

    public static Sender of(Member sender) {
        return new Sender(sender.getId(), sender.getName(), sender.getProfileImage());
    }
}
