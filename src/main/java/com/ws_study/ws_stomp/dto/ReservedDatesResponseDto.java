package com.ws_study.ws_stomp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservedDatesResponseDto {
    private List<LocalDate> reservedDates;
}
