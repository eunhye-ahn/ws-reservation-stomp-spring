package com.ws_study.ws_stomp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ReservedTimesResponseDto {
    @JsonFormat(pattern = "HH:mm:ss")
    private List<LocalTime> reservedTime;
}
