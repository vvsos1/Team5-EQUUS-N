package com.feedhanjum.feedback.application.port.in.retrospect.command;

import com.feedhanjum.core.SelfValidating;
import lombok.Getter;

@Getter
public class WriteRetrospectCommand extends SelfValidating<WriteRetrospectCommand> {
    private final String title;
    private final String content;
    private final Long writerId;
    private final Long teamId;

    public WriteRetrospectCommand(String title, String content, Long writerId, Long teamId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.teamId = teamId;
        validateSelf();
    }
}
