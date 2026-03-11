package com.ws_study.ws_stomp.exception;


import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException {
    private final int status;
    private final String code;

    private ReservationException(String message, String code, int status){
        super(message);
        this.status = status;
        this.code = code;
    }

    public static ReservationException duplicated() {
        return new ReservationException("이미 예약된 시간입니다.", 409, "DUPLICATE_RESERVATION");
    }
}
