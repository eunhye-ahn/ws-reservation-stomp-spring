package com.ws_study.ws_stomp.controller;

import com.ws_study.ws_stomp.domain.Reservation;
import com.ws_study.ws_stomp.dto.ReservationRequestDto;
import com.ws_study.ws_stomp.dto.ReservationResponseDto;
import com.ws_study.ws_stomp.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;


    @GetMapping("/reserved-dates")
    public List<ReservationResponseDto> getAllReservations() {
        return reservationService.getFullDates();
    }

    @PostMapping
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {

    }

}
