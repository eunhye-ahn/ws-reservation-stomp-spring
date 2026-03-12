package com.ws_study.ws_stomp.controller;

import com.ws_study.ws_stomp.dto.response.ReservedDatesResponseDto;
import com.ws_study.ws_stomp.dto.response.ReservedTimesResponseDto;
import com.ws_study.ws_stomp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReservationWebSocketController {
    private final ReservationService reservationService;
    //마감된 시간조회
    @MessageMapping("/reservations/{date}/reservedTimes")
    @SendToUser("/topic/reservations/{date}/reservedTimes")
    public ReservedTimesResponseDto getReservedTimes(@DestinationVariable String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return reservationService.getReservedTimes(date);
    }
}
