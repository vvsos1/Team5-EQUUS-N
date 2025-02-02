package com.feedhanjum.back_end.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;

    @Embedded
    private ProfileImage profileImage;

    public Member(String name, String email, ProfileImage profileImage) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public void changeProfile(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof Member m)
            return getId() != null && Objects.equals(getId(), m.getId());
        return false;
    }

}
