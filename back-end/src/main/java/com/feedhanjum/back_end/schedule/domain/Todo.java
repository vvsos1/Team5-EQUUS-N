package com.feedhanjum.back_end.schedule.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class Todo {
    private String content;

    public Todo(String content) {
        this.content = content;
    }
}
