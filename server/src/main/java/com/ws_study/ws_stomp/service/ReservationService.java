package com.ws_study.ws_stomp.service;

import com.ws_study.ws_stomp.domain.Reservation;
import com.ws_study.ws_stomp.dto.ReservationRequestDto;
import com.ws_study.ws_stomp.dto.ReservedDatesResponseDto;
import com.ws_study.ws_stomp.dto.ReservedTimesResponseDto;
import com.ws_study.ws_stomp.exception.ReservationException;
import com.ws_study.ws_stomp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;

    public void save(ReservationRequestDto request) {
        Reservation reservation = new Reservation();

        //중복체크
        //existBy~는 boolean을 반환 -> 무조건 조건문
        if(reservationRepository.existsByStartAt(request.getStartAt())){
            throw ReservationException.duplicated();
        }
        //dto 누락
        if(request.getStartAt()==null){
            throw ReservationException.missingStartAt();
        }
        //endAt계산, Entity변환
        reservation.setStartAt(request.getStartAt());
        reservation.setEndAt(request.getStartAt().plusHours(1));

        //리포지토리의 세이브 호출
        reservationRepository.save(reservation);
    }

    //마감된 날짜 조회
    public ReservedDatesResponseDto getReservedDates() {
        List<LocalDate> reservedDates = reservationRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(r->r.getStartAt().toLocalDate()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() ==3)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        return new ReservedDatesResponseDto(reservedDates);
    }

    //마감된 시간조회
    public ReservedTimesResponseDto getReservedTimes(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<LocalTime> reservedTimes = reservationRepository.findAll()
                .stream()
                .filter(r -> r.getStartAt().toLocalDate().equals(date))
                .map(r -> r.getStartAt().toLocalTime())
                .collect(Collectors.toList());
        return new ReservedTimesResponseDto(reservedTimes);
    }
}
