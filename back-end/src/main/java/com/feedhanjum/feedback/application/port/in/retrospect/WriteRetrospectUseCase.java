package com.feedhanjum.feedback.application.port.in.retrospect;

import com.feedhanjum.feedback.application.port.in.retrospect.command.WriteRetrospectCommand;

public interface WriteRetrospectUseCase {
    void writeRetrospect(WriteRetrospectCommand command);
}
