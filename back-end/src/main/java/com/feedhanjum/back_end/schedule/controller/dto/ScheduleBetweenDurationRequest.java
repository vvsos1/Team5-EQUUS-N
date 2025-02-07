package com.feedhanjum.back_end.schedule.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ScheduleBetweenDurationRequest (
    Long teamId,
    @NotNull LocalDate startDay,
    @NotNull LocalDate endDay
){

}