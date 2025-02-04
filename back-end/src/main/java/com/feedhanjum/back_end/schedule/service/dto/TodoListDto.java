package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.Todo;

import java.util.List;

public record TodoListDto (Long memberId, String name, List<Todo> todos){
    public TodoListDto(ScheduleMember scheduleMember){
        this(scheduleMember.getMember().getId(), scheduleMember.getMember().getName(), scheduleMember.getTodos());
    }
}
