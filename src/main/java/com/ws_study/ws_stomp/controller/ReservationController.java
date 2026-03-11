package com.ws_study.ws_stomp.controller;


import com.ws_study.ws_stomp.dto.ReservationRequestDto;
import com.ws_study.ws_stomp.service.ReservationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    //예약생성
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.save(reservationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "code", "CREATED",
                        "message", "예약완료"
                ));
    }
}
