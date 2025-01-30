package com.feedhanjum.back_end.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberDetails {
    @Id
    @Column(name = "member_details_id")
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    public MemberDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
