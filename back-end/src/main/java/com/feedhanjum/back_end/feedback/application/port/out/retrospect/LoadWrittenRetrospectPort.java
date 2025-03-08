package com.feedhanjum.back_end.feedback.application.port.out.retrospect;

import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LoadWrittenRetrospectPort {
    Page<Retrospect> loadWrittenRetrospects(long writerId, @Nullable Long teamId, int page, int pageSize, Sort.Direction sortOrder);
}
