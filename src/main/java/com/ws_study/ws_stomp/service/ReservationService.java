package com.ws_study.ws_stomp.service;

import com.ws_study.ws_stomp.domain.Reservation;
import com.ws_study.ws_stomp.dto.ReservationRequestDto;
import com.ws_study.ws_stomp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;

    public void save(ReservationRequestDto request) {
        Reservation reservation = new Reservation();

        //중복체크
        //existBy~는 boolean을 반환 -> 무조건 조건문
        if(reservationRepository.existsByStartAt(request.getStartAt())){
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }

        //endAt계산, Entity변환
        reservation.setStartAt(request.getStartAt());
        reservation.setEndAt(request.getStartAt().plusHours(1));

        //리포지토리의 세이브 호출
        reservationRepository.save(reservation);
    }
}
