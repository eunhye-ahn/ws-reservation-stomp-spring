package com.ws_study.ws_stomp.controller;


import com.ws_study.ws_stomp.dto.request.ReservationRequestDto;
import com.ws_study.ws_stomp.dto.response.ReservedDatesResponseDto;
import com.ws_study.ws_stomp.dto.response.ReservedTimesResponseDto;
import com.ws_study.ws_stomp.service.ReservationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    //예약생성
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.save(reservationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //마감된 날짜 조회
    @GetMapping("/reservedDates")
    public ResponseEntity<?> getReservedDates() {
        ReservedDatesResponseDto response = reservationService.getReservedDates();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //마감된 시간 조회
    @GetMapping("reservedTimes")
    public ResponseEntity<?> getReservedTimes(@RequestParam LocalDate date) {
        ReservedTimesResponseDto response = reservationService.getReservedTimes(date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
