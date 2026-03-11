package com.ws_study.ws_stomp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startAt;
}
