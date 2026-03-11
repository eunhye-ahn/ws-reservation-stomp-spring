package com.ws_study.ws_stomp.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReservationException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    private ReservationException(String message, HttpStatus status, String code){
        super(message);
        this.status = status;
        this.code = code;
    }

    public static ReservationException duplicated() {
        return new ReservationException("이미 예약된 시간입니다.", HttpStatus.CONFLICT , "DUPLICATE_RESERVATION");
    }

    public static ReservationException missingStartAt() {
        return new ReservationException("시간이 입력되지 않았습니다.", HttpStatus.BAD_REQUEST, "MISSING_START_AT");
    }
}
