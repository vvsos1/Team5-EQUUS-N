package com.feedhanjum.feedback.adapter.out.persistence.retrospect;

import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class RetrospectJpaEntity {
    @Id
    @Column(name = "retrospect_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "writer_id")),
            @AttributeOverride(name = "name", column = @Column(name = "writer_name")),
            @AttributeOverride(name = "email", column = @Column(name = "writer_email")),
            @AttributeOverride(name = "profileImage.backgroundColor", column = @Column(name = "writer_background_color")),
            @AttributeOverride(name = "profileImage.image", column = @Column(name = "writer_image")),
    })
    private FeedbackMember writer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "team_id")),
            @AttributeOverride(name = "name", column = @Column(name = "team_name")),
    })
    private AssociatedTeam team;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public RetrospectJpaEntity(Long id, String title, String content, FeedbackMember writer, AssociatedTeam team, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.team = team;
        this.createdAt = createdAt;
    }
}
