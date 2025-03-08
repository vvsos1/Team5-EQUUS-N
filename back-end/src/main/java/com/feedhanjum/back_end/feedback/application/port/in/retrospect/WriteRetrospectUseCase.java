package com.feedhanjum.back_end.feedback.application.port.in.retrospect;

import com.feedhanjum.back_end.feedback.application.port.in.retrospect.command.WriteRetrospectCommand;

public interface WriteRetrospectUseCase {
    void writeRetrospect(WriteRetrospectCommand command);
}
