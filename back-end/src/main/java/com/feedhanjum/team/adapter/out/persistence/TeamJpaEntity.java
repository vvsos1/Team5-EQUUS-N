package com.feedhanjum.team.adapter.out.persistence;

import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE teams SET deleted = true WHERE team_id = ?")
@Table(name = "teams")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
class TeamJpaEntity {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deleted")
    private final boolean deleted = false;

    @Column(name = "feedback_type")
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Column(name = "leader_id")
    private Long leaderId;

    @Column(name = "team_members", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<MembershipJpaEntity> memberships = new ArrayList<>();

    public TeamJpaEntity(Long id, String name, LocalDate startDate, LocalDate endDate, FeedbackType feedbackType, Long leaderId, List<MembershipJpaEntity> memberships) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedbackType = feedbackType;
        this.leaderId = leaderId;
        this.memberships = memberships;
    }
}
