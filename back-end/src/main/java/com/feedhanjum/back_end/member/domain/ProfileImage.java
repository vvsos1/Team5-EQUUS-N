package com.feedhanjum.back_end.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage {
    private String backgroundColor;
    private String image;

    public ProfileImage(String backgroundColor, String image) {
        this.backgroundColor = backgroundColor;
        this.image = image;
    }
}
