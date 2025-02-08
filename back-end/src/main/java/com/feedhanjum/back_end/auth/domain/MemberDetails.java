package com.feedhanjum.back_end.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDetails {
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_NAME_BYTE = 1;
    public static final int MAX_NAME_BYTE = 10;

    @Id
    @Column(name = "member_details_id")
    private Long id;

    private String email;
    private String password;

    public void changePassword(String newPassword) {
        password = newPassword;
    }
}
