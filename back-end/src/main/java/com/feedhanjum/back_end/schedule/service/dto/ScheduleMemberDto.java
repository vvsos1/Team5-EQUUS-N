package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.Todo;

import java.util.List;

public record ScheduleMemberDto (Long memberId, List<Todo> todos){
    public ScheduleMemberDto(ScheduleMember scheduleMember){
        this(scheduleMember.getMember().getId(), scheduleMember.getTodos());
    }
}
