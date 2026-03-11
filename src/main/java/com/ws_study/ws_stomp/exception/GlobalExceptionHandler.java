package com.ws_study.ws_stomp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class GlobalExceptionHandler {

    //중복체크
    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<Map<String,String>> handleReservationException(ReservationException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
    }
}
