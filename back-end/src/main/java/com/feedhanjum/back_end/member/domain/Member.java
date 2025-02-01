package com.feedhanjum.back_end.member.domain;

import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.Retrospect;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.team.domain.TeamMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "member")
    private final List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<ScheduleMember> scheduleMembers = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private final List<Retrospect> retrospects = new ArrayList<>();

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
