package com.ws_study.ws_stomp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //예약 생성 예외처리 => 중복체크(409),400
    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<Map<String,String>> handleReservationException(ReservationException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
    }

    //전역 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "code", "INTERNAL_SERVER_ERROR",
                        "message", "서버에 오류가 발생했습니다."
                ));
    }
}
