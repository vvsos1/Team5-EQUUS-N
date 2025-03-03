package com.feedhanjum.back_end.feedback.application.port.out;

import com.feedhanjum.back_end.feedback.domain.Receiver;

public interface LoadReceiverPort {
    Receiver loadReceiver(Long receiverId);
}
