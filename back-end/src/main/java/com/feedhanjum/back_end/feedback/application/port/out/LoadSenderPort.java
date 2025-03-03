package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.Sender;

public interface LoadSenderPort {
    Sender loadSender(Long senderId);
}
