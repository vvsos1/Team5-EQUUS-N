package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.repository.dto.ScheduleProjectionDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleMemberNestedDto{
    private Long scheduleMemberId;
    private Long memberId;
    private String memberName;
    private List<String> todoList = new ArrayList<>();

    public ScheduleMemberNestedDto(ScheduleProjectionDto scheduleProjectionDto){
        scheduleMemberId = scheduleProjectionDto.getScheduleMemberId();
        memberId = scheduleProjectionDto.getMemberId();
        memberName = scheduleProjectionDto.getMemberName();
    }

    public void addTodo(String todo){
        todoList.add(todo);
    }
}
