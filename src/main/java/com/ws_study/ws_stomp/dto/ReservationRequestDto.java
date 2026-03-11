package com.ws_study.ws_stomp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    @JsonProperty("start_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;
}
