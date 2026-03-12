package com.ws_study.ws_stomp.event;


import com.ws_study.ws_stomp.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationEvent {
    private final Reservation reservation;
}
