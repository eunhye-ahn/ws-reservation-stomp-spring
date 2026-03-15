package com.ws_study.ws_stomp.service;

import com.ws_study.ws_stomp.domain.Reservation;
import com.ws_study.ws_stomp.dto.request.ReservationRequestDto;
import com.ws_study.ws_stomp.dto.response.ReservedDatesResponseDto;
import com.ws_study.ws_stomp.dto.response.ReservedTimesResponseDto;
import com.ws_study.ws_stomp.event.ReservationEvent;
import com.ws_study.ws_stomp.exception.ReservationException;
import com.ws_study.ws_stomp.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void save(ReservationRequestDto request) {

        //dto 누락
        if(request.getStartAt()==null){
            throw ReservationException.missingStartAt();
        }

        Reservation reservation = new Reservation();
        reservation.setStartAt(request.getStartAt());
        reservation.setEndAt(request.getStartAt().plusHours(1));

        //existsBy 대신 db유니크로 중복예약 처리
        try{
            reservationRepository.save(reservation);
        }catch(DataIntegrityViolationException e){
            throw ReservationException.duplicated();
        }

        //리포지토리의 세이브 호출
        reservationRepository.save(reservation);

        applicationEventPublisher.publishEvent(new ReservationEvent(reservation));
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
    public ReservedTimesResponseDto getReservedTimes(LocalDate date) {
        List<LocalTime> reservedTimes = reservationRepository.findAll()
                .stream()
                .filter(r -> r.getStartAt().toLocalDate().equals(date))
                .map(r -> r.getStartAt().toLocalTime())
                .collect(Collectors.toList());
        return new ReservedTimesResponseDto(reservedTimes);
    }
}
